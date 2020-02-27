package com.redescooter.ses.web.ros.vo.sales;

import java.util.Date;
import java.util.List;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.bom.parts.PartListEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private String productName;

    @ApiModelProperty(value = "产品编码")
    private String productN;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "法国报价")
    private String productFrPrice;

    @ApiModelProperty(value = "英国报价")
    private String productEnPrice;

    @ApiModelProperty(value = "刷新时间")
    private Date refuseTime;

    @ApiModelProperty(value = "部件列表")
    private List<PartListEnter> partList;
}
