package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassNamePurchaseCombineDetail
 * @Description
 * @Author Aleks
 * @Date2020/10/23 20:23
 * @Version V1.0
 **/
@Data
@ApiModel(value = "采购单组装件详情出参",description = "采购单组装件详情出参")
public class PurchaseCombineDetailResult extends GeneralResult {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "组装件名称(英文名称)")
    private String combinName;

    @ApiModelProperty(value = "组装件id")
    private Long productionCombinBomId;

    @ApiModelProperty("组装件编号")
    private String bomNo;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "采购单价")
    private BigDecimal unitPrice;



}
