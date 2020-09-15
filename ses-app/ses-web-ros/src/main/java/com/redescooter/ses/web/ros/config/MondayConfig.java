package com.redescooter.ses.web.ros.config;

import lombok.Data;
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

    // 数据备份工作区域
    private String workspaceBackId;

    //模板Id
    private String templeteId;

    //预定单板子名称
    private String orderFormBoardName;

    private String orderFormBoardBackName;

    //板子内分组名称
    private String orderFormGroupName;

    //联系我们板子名称
    private String contactUsBoardName;

    private String contactUsBoardBackName;

    //联系我们分组名称
    private String contactUsGroupName;

    //订阅邮件板子名称
    private String subEmailBoardName;

    private String subEmailBoardBackName;

    //订阅邮件分组
    private String subEmailGroupName;

    //是否加载模版
    private Boolean loadTemplate=Boolean.FALSE;
}
