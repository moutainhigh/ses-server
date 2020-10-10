package com.redescooter.ses.api.common.vo.base;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameEditPasswordEnter
 * @Description
 * @Author Aleks
 * @Date2020/5/26 14:39
 * @Version V1.0
 **/

@ApiModel(value = "Reset Password Enter", description = "Reset Password Enter")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class WebResetPasswordEnter extends GeneralEnter {

    @ApiModelProperty(value = "Original password")
    private String oldPassword;

    @ApiModelProperty(value = "New password")
    @NotNull(code = ValidationExceptionCode.PASSWORD_IS_EMPTY,message = "密码为空")
    private String newPassword;

    @ApiModelProperty(value = "Enter again")
    @NotNull(code = ValidationExceptionCode.PASSWORD_IS_EMPTY,message = "密码为空")
    private String confirmPassword;

}
