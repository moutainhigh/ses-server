package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductPriceDetailResult
 * @description: ProductPriceDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/28 13:08
 */
@ApiModel(value = "产品价格详情", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductPriceDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "法国报价")
    private String productFrPrice;

    @ApiModelProperty(value = "报价单位")
    private String productFrUnit;

    @ApiModelProperty(value = "英国报价")
    private String productEnPrice;

    @ApiModelProperty(value = "报价单位")
    private String productEnUnit;
}
