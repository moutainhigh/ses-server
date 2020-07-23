package com.redescooter.ses.web.ros.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 * @ClassName:MondayConfig
 * @description: MondayConfig
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/09 16:41
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "monday",ignoreUnknownFields = true)
public class MondayConfig {

    private String url;

    private String authorization;

    private String paramQuery;

    private String paramVariables;

    private HttpMethod httpMethod;

    private MediaType mediaType;

    private String orderFormBoardName;

    private String orderFormGroupName;

    private String contactUsBoardName;

    private String contactUsGroupName;
}
