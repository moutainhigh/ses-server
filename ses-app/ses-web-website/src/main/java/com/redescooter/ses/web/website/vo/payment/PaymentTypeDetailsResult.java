package com.redescooter.ses.web.website.vo.payment;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:38 上午
 * @Description 支付方式结果集出参
 **/
@ApiModel(value = "支付方式结果集出参", description = "支付方式结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentTypeDetailsResult extends GeneralResult {
    /**
     * 主建
     */
    @ApiModelProperty(value = "paymentTypeId")
    private Long paymentTypeId;

    @ApiModelProperty(value = "payment_name")
    private String paymentName;

    @ApiModelProperty(value = "payment_code")
    private String paymentCode;

    @ApiModelProperty(value = "其他参数配置")
    private String otherParam;

    @ApiModelProperty(value = "remarks")
    private String remarks;

}
