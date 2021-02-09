package com.redescooter.ses.api.foundation.vo.mail;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/2/9 2:00 下午
 * @Description 邮件模板参数结果集
 **/
@ApiModel(value = "邮件模板参数结果集", description = "邮件模板参数结果集")
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Data
public class MailTemplateParameterInfoResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "对应key，不可为空")
    private String paramKey;

    @ApiModelProperty(value = "对应值域，可为空")
    private String paramValue;
}