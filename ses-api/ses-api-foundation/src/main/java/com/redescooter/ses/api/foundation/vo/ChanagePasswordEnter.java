package com.redescooter.ses.api.foundation.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: ChanagePasswordEnter
 * @author: Alex
 * @create: 2019/06/19 13:14
 */
@ApiModel(value = "修改密码入参", description = "修改密码入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ChanagePasswordEnter extends GeneralEnter {

    @ApiModelProperty(value = "用户名")
    private String email;

    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    private String newPassword;

    @ApiModelProperty(value = "重复新密码")
    private String confirmNewPassword;
}
