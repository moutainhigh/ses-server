package com.redescooter.ses.api.foundation.vo.login;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: LoginEnter
 * @author: Darren
 * @create: 2019/01/16 09:56
 */


@ApiModel(value = "登录入参", description = "登录入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class LoginEnter extends GeneralEnter {

    @ApiModelProperty(value = "账户类型",hidden = true)
    private int loginType = 1;

    @ApiModelProperty(value = "登录名")
    @NotNull(code = ValidationExceptionCode.EMAIL_IS_EMPTY,message = "用户名为空")
    private String loginName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

}
