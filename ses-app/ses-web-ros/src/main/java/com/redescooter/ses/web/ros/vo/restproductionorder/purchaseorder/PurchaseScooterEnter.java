package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassNamePurchaseScooterEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/23 19:39
 * @Version V1.0
 **/
@Data
@ApiModel(value = "采购单车辆产品新增编辑入参",description = "采购单车辆产品新增编辑入参")
public class PurchaseScooterEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "车型（规格分组）的id")
    private Long groupId;

    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "采购单价")
    private BigDecimal unitPrice;



}
