package com.redescooter.ses.api.foundation.vo.mail;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/2/9 1:59 下午
 * @Description 邮件模板系统参数实体
 **/
@ApiModel(value = "邮件模板系统结果集", description = "邮件模板系统结果集")
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Data
public class MailTemplateSystemInfoResult extends GeneralResult {

    @ApiModelProperty(value = "系统编码，格式：systemId::appId", hidden = true)
    private String code;

    @ApiModelProperty(value = "系统ID")
    private String systemId;

    @ApiModelProperty(value = "应用ID")
    private String appId;

    List<MailTemplateParameterInfoResult> parameterInfoList = new ArrayList<>();
}
