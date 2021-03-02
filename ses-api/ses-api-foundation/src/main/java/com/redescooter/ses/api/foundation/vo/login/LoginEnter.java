package com.redescooter.ses.api.foundation.vo.login;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: LoginEnter
 * @author: Darren
 * @create: 2019/01/16 09:56
 */


@ApiModel(value = "Log in", description = "Log in")
@Data //生成getter,setter等函数
@AllArgsConstructor//生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class LoginEnter extends GeneralEnter {

    @ApiModelProperty(value = "Account type", hidden = true)
    private int loginType = 1;

    @ApiModelProperty(value = "Login name")
    @NotNull(code = ValidationExceptionBaseCode.NAME_ILLEAGE, message = "用户名为空")
    private String loginName;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "Verification Code",hidden = true)
    private String code;

    @ApiModelProperty(value = "Customer Id",hidden = true)
    private Long customerId;

}
