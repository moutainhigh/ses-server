package com.redescooter.ses.mobile.wh.fr.vo;

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
 * @Date 2021/5/10 14:10
 */
@Data
@ApiModel(value = "app登录入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AppLoginEnter extends GeneralEnter {

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "密码")
    private String password;

}
