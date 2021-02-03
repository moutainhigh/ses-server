package com.redescooter.ses.web.website.vo.login;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/2/1 4:10 下午
 * @Description 官网忘记密码
 **/
@ApiModel(value = "Set PasswordEnter", description = "设置密码")
@Data
@EqualsAndHashCode(callSuper = true)
public class SiteSetPasswordEnter extends GeneralEnter {

    @NotNull(code = ValidationExceptionBaseCode.PASSWORD_IS_EMPTY, message = "密码为空")
    @ApiModelProperty(value = "Second new password")
    private String newPassword;

    @NotNull(code = ValidationExceptionBaseCode.PASSWORD_IS_EMPTY, message = "密码为空")
    @ApiModelProperty(value = "Second confirmation password")
    private String cfmPassword;

}
