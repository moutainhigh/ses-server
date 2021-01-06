package com.redescooter.ses.web.website.vo.payment;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:38 上午
 * @Description 新增支付方式入参
 **/

@ApiModel(value = "新增支付方式入参", description = "新增支付方式入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddPaymentTypeEnter extends GeneralEnter {

    /**
     * 支付方式名称
     */
    @ApiModelProperty(value = "支付方式名称")
    private String paymentName;

    /**
     * 其他参数配置
     */
    @ApiModelProperty(value = "其他参数配置")
    private String otherParam;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
