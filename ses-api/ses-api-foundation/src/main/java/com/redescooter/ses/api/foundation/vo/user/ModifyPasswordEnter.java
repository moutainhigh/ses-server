package com.redescooter.ses.api.foundation.vo.user;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: ChanagePasswordEnter
 * @author: Alex
 * @create: 2019/06/11 15:45
 */
@ApiModel(value = "修改账号密码入参", description = "修改账号密码入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ModifyPasswordEnter extends GeneralEnter {

    @ApiModelProperty(value = "用户名")
    private String loginName;

    @NotNull(code = ValidationExceptionBaseCode.PASSWORD_IS_EMPTY,message = "密码为空")
    @ApiModelProperty(value = "老密码")
    private String oldPassword;

    @NotNull(code = ValidationExceptionBaseCode.PASSWORD_IS_EMPTY,message = "密码为空")
    @ApiModelProperty(value = "新密码")
    private String newPassword;


    @ApiModelProperty(value = "验证码")
    private String code;
}
