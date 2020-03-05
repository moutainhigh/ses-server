package com.redescooter.ses.web.ros.vo.sales;

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
 * @ClassName:ProductListResult
 * @description: ProductListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 17:34
 */
@ApiModel(value = "产品列表出参", description = "产品列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品名字")
    private String productNumber;

    @ApiModelProperty(value = "产品类型")
    private String productType;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "法文名称")
    private String frName;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "产品照片")
    private String pictures;

    @ApiModelProperty(value = "产品型号")
    private String model;

    @ApiModelProperty(value = "产品颜色")
    private String color;

    @ApiModelProperty(value = "总条目数")
    private String sumPartsQty;

    @ApiModelProperty(value = "法国报价")
    private String productFrPrice;

    @ApiModelProperty(value = "报价单位")
    private String productFrUnit;

    @ApiModelProperty(value = "英国报价")
    private String productEnPrice;

    @ApiModelProperty(value = "报价单位")
    private String productEnUnit;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "刷新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date refuseTime;
}
