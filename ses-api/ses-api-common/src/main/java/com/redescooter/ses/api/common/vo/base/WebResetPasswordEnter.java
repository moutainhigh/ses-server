package com.redescooter.ses.api.common.vo.base;

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

    @ApiModelProperty(value = "密码")
    private String newPassword;

}
