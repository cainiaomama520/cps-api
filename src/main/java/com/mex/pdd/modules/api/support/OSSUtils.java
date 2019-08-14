package com.mex.pdd.modules.api.support;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.mex.pdd.base.common.utils.ZipUtils;
import com.mex.pdd.base.common.utils.excel.ExportExcel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by David
 * on 2017/4/25
 */
@Component
@ConfigurationProperties(prefix = "app.oss")
public class OSSUtils {
    private Logger logger = LoggerFactory.getLogger(OSSUtils.class);

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String prefix;
    private String domain;
    private String downloadDir;
    public final int partSize = 1024 * 100;//100k

    private OSSClient ossClient;

    public <T> String uploadZipExcel(Class<T> clazz, List<T> list, String filePrefix) {
        return uploadZipExcel(clazz, list, filePrefix, null);
    }

    public <T> String uploadZipExcel(Class<T> clazz, List<T> list, String filePrefix, List<String> header) {
        try {
            String downloadDir = getDownloadDir();
            String fileName = filePrefix + ".xlsx";
            String filePath = downloadDir + File.separator + fileName;
            FileOutputStream os = new FileOutputStream(filePath);
            new ExportExcel(null, clazz, header, 1, null).setDataList(list).write(os);
            String zipFleName = filePrefix + ".zip";
            String zipPath = downloadDir + File.separator + zipFleName;
            ZipUtils.zipFile(filePath, zipPath);
            String key = "task/" + zipFleName;
            putObject(key, new File(zipPath));
            return getDomain() + key;
        } catch (IOException e) {
            logger.error("upload zip excel to oss error", e);
        }
        return null;
    }

    public String doDownloadIfNotExist(String url, String downloadPath) {
        Path path = Paths.get(downloadPath);
        if (!path.toFile().exists()) {
            try {
                java.nio.file.Files.createFile(path);
//                Files.createFile(Paths.get(relativePath + ".tmp"));
                URL urlObj = new URL(url);
                String key = urlObj.getPath().substring(1);
                ObjectMetadata objectMetadata = downloadFile(key, downloadPath);
            } catch (Exception e) {
                logger.error("download file error.", e);
            }
        } else {
            logger.info("file exist, relativePath=" + downloadPath);
        }
        return downloadPath;
    }

    /**
     * 从 OSS 下载
     *
     * @param key          对象在桶中对应的键
     * @param downloadFile 本地保存路径
     * @return ObjectMetadata
     */
    public ObjectMetadata downloadFile(String key, String downloadFile) {
        return this.downloadFile(key, downloadFile, 10, true);
    }

    /**
     * 从 OSS 下载
     *
     * @param key              对象在桶中对应的键
     * @param downloadFile     本地保存路径
     * @param taskNum          线程数
     * @param enableCheckpoint 是否断点续传
     * @return ObjectMetadata
     */
    public ObjectMetadata downloadFile(String key, String downloadFile, int taskNum, boolean enableCheckpoint) {
        if (StringUtils.isBlank(downloadFile)) {
            throw new IllegalArgumentException("File be saved path is null");
        }
        ObjectMetadata objectMetadata = null;
        try {
            // 下载请求，10个任务并发下载，启动断点续传
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, key);
            downloadFileRequest.setDownloadFile(downloadFile);
            downloadFileRequest.setTaskNum(taskNum);
            downloadFileRequest.setEnableCheckpoint(enableCheckpoint);

            // 下载文件
            DownloadFileResult downloadRes = ossClient.downloadFile(downloadFileRequest);

            // 下载成功时，会返回文件的元信息
            objectMetadata = downloadRes.getObjectMetadata();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        return objectMetadata;
    }

    public static class Temp {
        private MultipartFile file;
        private File tempDir;
        private File tempFile;

        public Temp(MultipartFile file) {
            this.file = file;
        }

        public File getTempDir() {
            return tempDir;
        }

        public File getTempFile() {
            return tempFile;
        }

        public Temp invoke() {
            tempDir = Files.createTempDir();
            String originalFilename = file.getOriginalFilename();
            tempFile = new File(tempDir + File.separator + getFileNameWithoutExtend(originalFilename) + "-" + System.currentTimeMillis() + "." + getExtend(originalFilename));
            return this;
        }
    }

    @PostConstruct
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    public String uploadWithDatePath(String uuid, File file) {
        String key = Stream.of(prefix, getDatePath(), uuid, file.getName()).collect(Collectors.joining("/"));
        return putObject(key, file);
    }

    public String uploadWithDatePath(File file) {
        String key = Stream.of(prefix, getDatePath(), file.getName()).collect(Collectors.joining("/"));
        return putObject(key, file);
    }

    /**
     * 下载文件 必须要close
     *
     * @param key
     * @return
     */
    public OSSObject getObject(String key) {
        OSSObject ossObject = ossClient.getObject(bucketName, key);
//        try {
//            ossObject.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return ossObject;
    }

    //简单上传
    public String putObject(String key, File file) {
        logger.info("upload to oss the key is : {}", key);
        ossClient.putObject(new PutObjectRequest(bucketName, key, file));
        return key;
    }


    public String getDatePath() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        return formater.format(new Date());
    }

    /**
     * 生成UUID的文件名
     *
     * @param multipartFile
     * @return
     */
    public String getFileName(MultipartFile multipartFile) {
        String fileName = "";
        if (!multipartFile.isEmpty()) {
            String extend = getExtend(multipartFile.getOriginalFilename());
            fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + extend;
        }
        return fileName;
    }


    public static String getExtend(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }


    public static String getFileNameWithoutExtend(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 是否是图片
     *
     * @param extend
     * @return
     */
    public boolean isImg(String extend) {
        ImmutableList<String> list = ImmutableList.of("jpg", "jpeg", "bmp", "gif", "png", "tif");
        return list.contains(extend);
    }

    /**
     * 是否是图片
     *
     * @param inputStream
     * @return
     */
    public boolean isImage(InputStream inputStream) {
        boolean flag = false;
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (width != 0 && height != 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getPartSize() {
        return partSize;
    }

    public String getDownloadDir() {
        return downloadDir;
    }

    public void setDownloadDir(String downloadDir) {
        this.downloadDir = downloadDir;
    }
}

