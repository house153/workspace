package com.house.variety.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by luban
 * User: lic
 * Date: 2018/9/25
 * Time: 11:28
 * Desc xxx
 */
@Component
@ConfigurationProperties(prefix = "file.ftp")
@Data
public class FtpConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private String basePath;

    /**
     * nginx映射图片访问服务器
     */
    private String httpAccessPath;
}
