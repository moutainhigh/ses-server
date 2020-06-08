package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SignUpEnter
 * @description: SignUpEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 12:08
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SignUpEnter extends GeneralEnter {

    @ApiModelProperty(value = "姓名")
    @NotNull(code = ValidationExceptionCode.FIRST_NAME_IS_EMPTY,message = "姓名为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG,message = "字符过长")
    @MinimumLength(value = "2",code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT,message = "字符过短")
    private String firstName;

    @ApiModelProperty(value = "姓名")
    @NotNull(code = ValidationExceptionCode.LAST_NAME_IS_EMPTY,message = "姓名为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG,message = "字符过长")
    @MinimumLength(value = "2",code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT,message = "字符过短")
    private String lastName;

    @ApiModelProperty(value = "邮箱")
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.EMAIL_IS_EMPTY,message = "邮箱为空")
    private String email;

    @ApiModelProperty(value = "密码")
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.PASSWORD_IS_EMPTY,message = "密码为空")
    private String password;
}
