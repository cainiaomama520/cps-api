package com.mex.pdd.modules.admin.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.mex.pdd.base.common.entity.CreativeContentItem;
import com.mex.pdd.base.common.exception.RRException;
import com.mex.pdd.modules.api.support.OSSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/sys")
@Api(description = "文件上传控制器", tags = {"文件上传"})
public class FileUploadController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OSSUtils ossUtils;

    @ApiOperation("多图片上传")
    @PostMapping("/multi-upload")
    @ResponseBody
    public ResponseEntity<List<CreativeContentItem>> upload2(MultipartFile[] files) {
        List<CreativeContentItem> urls = Stream.of(files).map(file -> {
            try {
                OSSUtils.Temp temp = new OSSUtils.Temp(file).invoke();
                File tempFile = temp.getTempFile();
                File tempDir = temp.getTempDir();
                Files.asByteSink(tempFile).writeFrom(file.getInputStream());

                BufferedImage bimg = ImageIO.read(tempFile);
                int width = bimg.getWidth();
                int height = bimg.getHeight();
                String key = ossUtils.uploadWithDatePath("", tempFile);
                FileUtils.deleteDirectory(tempDir);
                String url = ossUtils.getDomain() + key;
                return new CreativeContentItem().setH(height).setW(width).setUrl(url);
            } catch (Exception e) {
                logger.error("upload load file error.", e);
                throw new RRException("upload load file error");
            }
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(urls);
    }

    @ApiOperation("多图片上传")
    @PostMapping("/multi-upload1")
    @ResponseBody
    public ResponseEntity<CreativeContentItem> multuUpload1(@RequestParam("file") MultipartFile file) {
        try {
            OSSUtils.Temp temp = new OSSUtils.Temp(file).invoke();
            File tempFile = temp.getTempFile();
            File tempDir = temp.getTempDir();
            Files.asByteSink(tempFile).writeFrom(file.getInputStream());

            BufferedImage bimg = ImageIO.read(tempFile);
            int width = bimg.getWidth();
            int height = bimg.getHeight();
            String key = ossUtils.uploadWithDatePath("", tempFile);
            FileUtils.deleteDirectory(tempDir);
            String url = ossUtils.getDomain() + key;
            return ResponseEntity.ok(new CreativeContentItem().setH(height).setW(width).setUrl(url));
        } catch (Exception e) {
            logger.error("upload load file error.", e);
            throw new RRException("upload load file error");
        }
    }

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Map<String, String>> upload2(@RequestParam("file") MultipartFile file) {
        String bannerUrl = "";
        try {
            OSSUtils.Temp temp = new OSSUtils.Temp(file).invoke();
            File tempFile = temp.getTempFile();
            File tempDir = temp.getTempDir();

            Files.asByteSink(tempFile).writeFrom(file.getInputStream());
            String key = ossUtils.uploadWithDatePath("lottery-detail", tempFile);
            FileUtils.deleteDirectory(tempDir);
            bannerUrl = ossUtils.getDomain() + key;
        } catch (Exception e) {
            logger.error("upload load file error.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("upload load file error").build();
        }
        Map<String, String> data = ImmutableMap.of("url", bannerUrl);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/uploadBase64")
    @ResponseBody
    public ResponseEntity<Map<String, String>> base64UpLoad(@RequestBody String body) {
        String bannerUrl;
        try {
            JSONObject jsonObject = JSON.parseObject(body);
            String base64Data = jsonObject.getString("base64Data");
            String dataPrix = "";
            String data = "";
            if (base64Data == null || "".equals(base64Data)) {
                throw new Exception("上传失败，上传图片数据为空");
            } else {
                String[] d = base64Data.split("base64,");
                if (d.length == 2) {
                    dataPrix = d[0];
                    data = d[1];
                } else {
                    throw new Exception("上传失败，数据不合法");
                }
            }
            String suffix = "";
            if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) { //data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = ".jpg";
            } else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) { //data:image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            } else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) { //data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            } else if ("data:image/png;".equalsIgnoreCase(dataPrix)) { //data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            } else {
                throw new Exception("上传图片格式不合法");
            }
            String tempFileName = UUID.randomUUID().toString() + suffix;
            byte[] bs = Base64Utils.decodeFromString(data);
            File tempDir = FileUtils.getTempDirectory();
            File tempFile = new File(tempDir + File.separator + DateTime.now().toString("yyyyMMdd") + File.separator + tempFileName);
            FileUtils.writeByteArrayToFile(tempFile, bs);
            String key = ossUtils.uploadWithDatePath("lottery-detail", tempFile);
            bannerUrl = ossUtils.getDomain() + key;
            FileUtils.forceDelete(tempFile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("upload load file error").build();
        }

        Map<String, String> data = ImmutableMap.of("url", bannerUrl);
        return ResponseEntity.ok().body(data);
    }
}
