package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/22 11:52
 */
@ApiModel(value = "刷新token入参", description = "刷新token入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class RefreshTokenEnter extends GeneralEnter {

    @ApiModelProperty(value = "刷新token", required = true)
    private String refreshToken;

}
