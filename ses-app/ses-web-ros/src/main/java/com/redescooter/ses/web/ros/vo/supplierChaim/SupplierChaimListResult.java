package com.redescooter.ses.web.ros.vo.supplierChaim;

import java.sql.Date;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName:SupplierChaimListResult
 * @description: SupplierChaimListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 14:47
 */
@ApiModel(value = "供应链列表", description = "供应链列表")
public class SupplierChaimListResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品名字")
    private String productEnName;

    @ApiModelProperty(value = "产品名字")
    private String productCnName;

    @ApiModelProperty(value = "产品名字")
    private String productFrName;

    @ApiModelProperty(value = "产品编码")
    private String productN;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "价格")
    private String productPrice;

    @ApiModelProperty(value = "货币单位")
    private String unit;

    @ApiModelProperty(value = "刷新时间")
    private Date refuseTime;
}
