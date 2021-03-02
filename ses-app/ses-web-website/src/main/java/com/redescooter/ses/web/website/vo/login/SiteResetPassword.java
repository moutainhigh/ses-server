package com.redescooter.ses.web.website.vo.login;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/2/1 4:26 下午
 * @Description 官网重置密码
 **/
@ApiModel(value = "Site Reset Password", description = "重置密码")
@Data
@EqualsAndHashCode(callSuper = true)
public class SiteResetPassword extends SiteSetPasswordEnter {

    @NotNull(code = ValidationExceptionBaseCode.PASSWORD_IS_EMPTY, message = "密码为空")
    @ApiModelProperty(value = "Second old password")
    private String oldPassword;

}
