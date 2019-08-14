package com.mex.pdd.base.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    public static void main(String[] args) throws IOException {
//        String sourceFile = "/Users/wangdavid/Downloads/脉脉";
//        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("/Users/wangdavid/Downloads/dirCompressed.zip"));
//        File fileToZip = new File(sourceFile);
//        zipFile(fileToZip, fileToZip.getPIdNameList(), zipOut);
//        zipOut.close();
//        zipFile("/Users/wangdavid/Downloads/脉脉", "/Users/wangdavid/Downloads/dirCompressed.zip");
        zipFile("/Users/wangdavid/Downloads/test.xlsx", "/Users/wangdavid/Downloads/dirCompressed123.zip");
    }

    public static void zipFile(String src, String dest) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(dest));
        File file = new File(src);
        zipFile(file, file.getName(), zipOut);
        zipOut.close();

    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}