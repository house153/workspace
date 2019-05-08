package com.house.variety.pojo;

import com.house.variety.util.StringUtils;

import java.io.*;
import java.nio.file.Files;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:28
 * @Description:
 */
public class FileInfo implements Serializable {
    private String fileName;
    private String extension;
    private Long fileSize;
    private InputStream inputStream;

    public FileInfo(String fileName, Long fileSize, InputStream inputStream) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.inputStream = inputStream;
    }

    public FileInfo(String fileName, Long fileSize, InputStream inputStream, String extension) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.inputStream = inputStream;
        this.extension = extension;
    }

    public int transferTo(File desc) throws IOException {
        OutputStream out = Files.newOutputStream(desc.toPath());
        return this.transferTo(out);
    }

    public int transferTo(OutputStream out) throws IOException {
        int byteCount = 0;
        if (StringUtils.isNull(out)) {
            return byteCount;
        } else {
            byte[] buffer = new byte[4096];

            int bytesRead;
            for(boolean var5 = true; (bytesRead = this.getInputStream().read(buffer)) != -1; byteCount += bytesRead) {
                out.write(buffer, 0, bytesRead);
            }

            out.flush();
            return byteCount;
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}
