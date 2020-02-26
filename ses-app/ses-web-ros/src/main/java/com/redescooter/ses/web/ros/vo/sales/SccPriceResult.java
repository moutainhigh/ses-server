package com.redescooter.ses.web.ros.vo.sales;

import java.util.Date;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SccProductPriceResult
 * @description: SccProductPriceResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 18:11
 */
@ApiModel(value = "产品报价出参", description = "产品报价出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SccPriceResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "法国报价")
    private String productFrPrice;


    @ApiModelProperty(value = "英国报价")
    private String productEnPrice;

    @ApiModelProperty(value = "更新时间")
    private Date refuseTime;
}
