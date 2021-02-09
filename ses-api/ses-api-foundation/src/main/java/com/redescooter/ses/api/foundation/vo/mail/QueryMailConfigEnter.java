package com.redescooter.ses.api.foundation.vo.mail;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author jerry
 * @Date 2021/2/9 1:15 下午
 * @Description 邮件模板参数入参
 **/
@ApiModel(value = "模板参数查询入参", description = "模板参数查询入参")
@Data
public class QueryMailConfigEnter extends GeneralEnter {

    @ApiModelProperty(value = "模板编号")
    @NotNull(code = ValidationExceptionBaseCode.MAIL_TEMPLATE_NUMBER_IS_EMPTY, message = "邮件编号不能为空")
    private Integer mailTemplateNo;
}
