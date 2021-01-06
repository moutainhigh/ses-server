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
@ApiModel(value = "产品型号结果出参", description = "产品型号结果出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductPriceDetailsResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "产品Id")
    private Long productId;

    @ApiModelProperty(value = "状态,0全额付款，1分期付款")
    private String priceType;

    @ApiModelProperty(value = "分期付款时间数，单位month")
    private String installmentTime;

    @ApiModelProperty(value = "销售价格 浮点型价格")
    private BigDecimal price;

}
