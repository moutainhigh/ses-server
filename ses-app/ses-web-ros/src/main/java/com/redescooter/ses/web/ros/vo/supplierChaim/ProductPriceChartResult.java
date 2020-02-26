package com.redescooter.ses.web.ros.vo.supplierChaim;

import java.util.List;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductPriceChartResult
 * @description: ProductPriceChartResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 15:43
 */
@ApiModel(value = "", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductPriceChartResult extends GeneralResult {

    @ApiModelProperty(value = "平均值")
    private String avg;

    @ApiModelProperty(value = "最大值")
    private String max;

    @ApiModelProperty(value = "产品报价")
    private List<ScProductPriceResult>  priceResultList;
}
