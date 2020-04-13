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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:SalesServiceResult
 * @description: SalesServiceResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/03 17:33
 */
@ApiModel(value = "增值产品列表出参", description = "增值产品列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SalesServiceResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品名字")
    private String service;

    @ApiModelProperty(value = "产品描述")
    private String desc;

    @ApiModelProperty(value = "法国报价")
    private BigDecimal productFrPrice;

    @ApiModelProperty(value = "报价单位")
    private String productFrUnit;

    @ApiModelProperty(value = "英国报价")
    private BigDecimal productEnPrice;

    @ApiModelProperty(value = "报价单位")
    private String productEnUnit;

    @ApiModelProperty(value = "刷新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date refuseTime;
}
