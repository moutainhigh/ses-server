package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNamePublicSecretResult
 * @Description
 * @Author Aleks
 * @Date2020/7/20 18:42
 * @Version V1.0
 **/
@Data
public class PublicSecretResult extends GeneralResult {

    @ApiModelProperty("前半段加密公钥")
    private String front;


    @ApiModelProperty("后半段加密公钥")
    private String behind;


}
