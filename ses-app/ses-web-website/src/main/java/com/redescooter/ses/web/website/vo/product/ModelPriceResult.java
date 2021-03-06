package com.redescooter.ses.web.website.vo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 9:16 上午
 * @Description 车型及价格结果集出参
 **/
@ApiModel(value = "Model Result", description = "车型及价格结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ModelPriceResult extends ProductModelDetailsResult {

    /**
     * 价格主键
     */
    @ApiModelProperty(value = "Price primary key")
    private Long productPriceId;

    /**
     * 价格类型
     */
    @ApiModelProperty(value = "Status, 0 full payment, 1 installment")
    private int priceType;

    /**
     * 销售价格 浮点型价格
     */
    @ApiModelProperty(value = "Total price")
    private BigDecimal price;

    /**
     * 起步价
     */
    @ApiModelProperty(value = "Starting price")
    private BigDecimal startPrice;

    /**
     * 电池数
     */
    @ApiModelProperty(value = "battery")
    private String battery;

    /**
     * 每期应付
     */
    @ApiModelProperty(value = "每期应付")
    private BigDecimal shouldPayPeriod;

}
