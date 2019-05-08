package com.house.variety.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.joda.time.DateTime;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:20
 * @Description:
 */
public class FtpUtil {
    public static final String SEPARATOR = "/";

    public FtpUtil() {
    }

    public static String datePath() {
        DateTime now = new DateTime();
        return "/" + now.toString("yyyy/MM/dd");
    }

    public static void main(String[] args) {
        System.out.println(File.separatorChar);
    }

    public static boolean uploadFile(String host, int port, String username, String password, String basePath, String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();

        boolean var11;
        try {
            ftp.connect(host, port);
            ftp.login(username, password);
            int reply = ftp.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                    String[] dirs = filePath.split("/");
                    String tempPath = basePath;
                    String[] var13 = dirs;
                    int var14 = dirs.length;

                    for(int var15 = 0; var15 < var14; ++var15) {
                        String dir = var13[var15];
                        if (null != dir && !"".equals(dir)) {
                            tempPath = tempPath + "/" + dir;
                            if (!ftp.changeWorkingDirectory(tempPath)) {
                                if (!ftp.makeDirectory(tempPath)) {
                                    boolean var17 = result;
                                    return var17;
                                }

                                ftp.changeWorkingDirectory(tempPath);
                            }
                        }
                    }
                }

                ftp.setFileType(2);
                ftp.enterLocalPassiveMode();
                if (!ftp.storeFile(filename, input)) {
                    var11 = result;
                    return var11;
                }

                input.close();
                ftp.logout();
                result = true;
                return result;
            }

            ftp.disconnect();
            var11 = result;
        } catch (Exception var30) {
            var30.printStackTrace();
            return result;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException var29) {
                    ;
                }
            }

        }

        return var11;
    }

    public static boolean downloadFile(String host, int port, String username, String password, String remotePath, String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();

        try {
            ftp.connect(host, port);
            ftp.login(username, password);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                boolean var28 = result;
                return var28;
            }

            ftp.changeWorkingDirectory(remotePath);
            FTPFile[] fs = ftp.listFiles();
            FTPFile[] var11 = fs;
            int var12 = fs.length;

            for(int var13 = 0; var13 < var12; ++var13) {
                FTPFile ff = var11[var13];
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }

            ftp.logout();
            result = true;
        } catch (IOException var26) {
            var26.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException var25) {
                    ;
                }
            }

        }

        return result;
    }
}
