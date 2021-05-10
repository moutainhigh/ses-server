package com.redescooter.ses.web.ros.vo.app;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 10:40
 */
@Data
@ApiModel(value = "仓库账号修改密码入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UpdatePasswordEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "账号")
    private String email;

    @ApiModelProperty(value = "新密码")
    private String newPassword;

    @ApiModelProperty(value = "确认密码")
    private String confirmNewPassword;

}
