package com.redescooter.ses.starter.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "oss",ignoreUnknownFields = true)
public class OssConfig {

    private String internalEndpoint;
    private String accessKeyId;
    private String secretAccesskey;
    private String defaultBucketName;
    private String publicEndpointDomain;
    private int maxFileSize;
}
