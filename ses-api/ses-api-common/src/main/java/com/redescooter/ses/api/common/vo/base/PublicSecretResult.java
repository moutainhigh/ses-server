package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNamePublicSecretResult
 * @Description
 * @Author Aleks
 * @Date2020/7/20 18:42
 * @Version V1.0
 **/
@ApiModel(value = "PublicSecret Result", description = "PublicSecret Result")
@Data
@EqualsAndHashCode(callSuper = false)
public class PublicSecretResult extends GeneralResult {

    @ApiModelProperty("First half encrypted public key")
    private String front;

    @ApiModelProperty("Second half encrypted public key")
    private String behind;


}
