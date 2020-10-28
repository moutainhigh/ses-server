package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassNamePurchaseScooterDetail
 * @Description
 * @Author Aleks
 * @Date2020/10/23 20:20
 * @Version V1.0
 **/
@Data
@ApiModel(value = "采购单车辆产品详情出参",description = "采购单车辆产品详情出参")
public class PurchaseScooterDetailResult extends GeneralResult {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "车型（规格分组）的id")
    private Long groupId;

    @ApiModelProperty(value = "车型（规格分组）的名称")
    private String groupName;

    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    @ApiModelProperty(value = "色值")
    private String colorValue;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "采购单价")
    private BigDecimal unitPrice;


}
