package com.redescooter.ses.api.foundation.vo.login;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassNameEmailLoginEnter
 * @Description
 * @Author Aleks
 * @Date2020/7/21 14:01
 * @Version V1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class EmailLoginEnter extends GeneralEnter{

    @ApiModelProperty(value = "登录名")
    @NotNull(code = ValidationExceptionBaseCode.EMAIL_IS_EMPTY,message = "用户名为空")
    private String loginName;

    @ApiModelProperty(value = "验证码")
    private String code;

}
