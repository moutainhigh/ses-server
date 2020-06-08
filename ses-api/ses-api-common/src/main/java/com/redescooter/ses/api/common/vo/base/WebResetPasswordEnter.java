package com.redescooter.ses.api.common.vo.base;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameEditPasswordEnter
 * @Description
 * @Author Aleks
 * @Date2020/5/26 14:39
 * @Version V1.0
 **/

@Data
public class WebResetPasswordEnter extends GeneralEnter {


    @ApiModelProperty(value = "原始密码")
    @NotNull(code = ValidationExceptionCode.PASSWORD_IS_EMPTY,message = "密码为空")
    private String oldPassword;

    @ApiModelProperty(value = "密码")
    @NotNull(code = ValidationExceptionCode.PASSWORD_IS_EMPTY,message = "密码为空")
    private String newPassword;

    @ApiModelProperty(value = "密码")
    @NotNull(code = ValidationExceptionCode.PASSWORD_IS_EMPTY,message = "密码为空")
    private String confirmPassword;

}
