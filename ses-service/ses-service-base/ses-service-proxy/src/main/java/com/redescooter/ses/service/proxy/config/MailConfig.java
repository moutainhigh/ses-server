package com.redescooter.ses.service.proxy.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: MailConfig
 * @author: Darren
 * @create: 2019/03/19 16:16
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfig {

    private String username;

    private String password;

    private String host;

    private Integer port;

    private String default_encoding;
}
