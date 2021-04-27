package com.redescooter.ses.web.website.vo.payment;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/27 17:49
 */
@ApiModel(value = "支付方式入参", description = "支付方式入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentTypeEnter extends GeneralEnter {

    @ApiModelProperty(value = "车型id")
    private Long modelId;

    @ApiModelProperty(value = "电池数")
    private String battery;

}
