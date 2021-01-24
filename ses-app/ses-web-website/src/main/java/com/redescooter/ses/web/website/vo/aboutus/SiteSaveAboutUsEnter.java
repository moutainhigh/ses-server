package com.redescooter.ses.web.website.vo.aboutus;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.website.exception.SiteValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/24 5:53 上午
 * @Description 官网联系我们
 **/
@ApiModel(value = "Contact our", description = "Contact our")
@Data
@EqualsAndHashCode(callSuper = true)
public class SiteSaveAboutUsEnter extends GeneralEnter {

    @ApiModelProperty(value = "email")
    @NotNull(code = SiteValidationExceptionCode.EMAIL_EMPTY, message = "邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "message")
    @NotNull(code = SiteValidationExceptionCode.MESSAGE_IS_EMPTY, message = "消息不能为空")
    private String message;

    @ApiModelProperty(value = "first name")
    @NotNull(code = SiteValidationExceptionCode.FIRST_NAME_IS_EMPTY, message = "姓名为空")
    @MinimumLength(value = "2", code = SiteValidationExceptionCode.CHARACTER_IS_TOO_SHORT, message = "长度过短")
    @MaximumLength(value = "40", code = SiteValidationExceptionCode.CHARACTER_IS_TOO_LONG, message = "长度过长")
    private String firstName;

    @ApiModelProperty(value = "last name")
    @NotNull(code = SiteValidationExceptionCode.LAST_NAME_IS_EMPTY, message = "姓名为空")
    @MinimumLength(value = "2", code = SiteValidationExceptionCode.CHARACTER_IS_TOO_SHORT, message = "长度过短")
    @MaximumLength(value = "40", code = SiteValidationExceptionCode.CHARACTER_IS_TOO_LONG, message = "长度过长")
    private String lastName;
}
