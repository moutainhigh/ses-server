package com.redescooter.ses.web.website.vo.payment;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description 支付方式出参
 * @Author Chris
 * @Date 2021/4/27 17:56
 */
@ApiModel(value = "支付方式出参", description = "支付方式出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentTypeResult extends GeneralResult {

    @ApiModelProperty(value = "租赁的集合")
    private List<LeaseResult> leaseList;

    @ApiModelProperty(value = "全款支付的对象")
    private FullPayResult fullPay;

    @ApiModelProperty(value = "分期支付的集合")
    private List<InstallmentPayResult> installmentPayList;

}
