package com.redescooter.ses.starter.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName:SendinBlueConfig
 * @description: SendinBlueConfig
 * @author: Alex
 * @Version：1.3
 * @create: 2020/06/08 19:53
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sendinblue",ignoreUnknownFields = true)
public class SendinBlueConfig {
    private String updateEnabled;

    private String listIds;

    private String accept;

    private String contentType;

    private String apiKey;

    private String url;

    private String mediaType;
}
