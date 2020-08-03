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
@ConfigurationProperties(prefix = "monday", ignoreUnknownFields = true)
public class MondayConfig {

    private String url;

    private String authorization;

    private String paramQuery;

    private String paramVariables;

    private HttpMethod httpMethod;

    private MediaType mediaType;

    //工作区域Id
    private String workspaceId;

    //模板Id
    private String templeteId;

    //预定单板子名称
    private String orderFormBoardName;

    //板子内分组名称
    private String orderFormGroupName;

    //联系我们板子名称
    private String contactUsBoardName;

    //联系我们分组名称
    private String contactUsGroupName;
}
