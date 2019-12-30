package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName:SetPassword
 * @description: SetPassword
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/24 14:33
 */
@ApiModel(value = "重置密码入参", description = "重置密码入参")
@Data
public class SetPasswordEnter<T> extends GeneralEnter {

    @ApiModelProperty(value = "业务Id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "密码")
    private String newPassword;

    @ApiModelProperty(value = "密码")
    private String confirmPassword;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "业务对象",hidden = true)
    private T t;
}
