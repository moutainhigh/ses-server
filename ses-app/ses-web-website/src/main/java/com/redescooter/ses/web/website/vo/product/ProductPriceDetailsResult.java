package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:35 上午
 * @Description 产品价格结果集出参
 **/
@ApiModel(value = "Product price result set output parameters", description = "产品价格结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductPriceDetailsResult extends GeneralResult {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "Product Model Id")
    private Long productModelId;

    @ApiModelProperty(value = "Status, 0 full payment, 1 installment")
    private String priceType;

    @ApiModelProperty(value = "Start Price")
    private BigDecimal startPrice;

    @ApiModelProperty(value = "Installment payment time, in month. If it is full payment, the default value is 0")
    private String installmentTime;

    @ApiModelProperty(value = "Sales Price")
    private BigDecimal price;

}
