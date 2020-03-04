package com.redescooter.ses.web.ros.vo.bom.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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

    @ApiModelProperty(value = "报价单位")
    private String productFrUnit;

    @ApiModelProperty(value = "英国报价")
    private String productEnPrice;

    @ApiModelProperty(value = "报价单位")
    private String productEnUnit;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date refuseTime;
}
