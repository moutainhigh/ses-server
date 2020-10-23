package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassNameAllocateOrderPartsDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 17:16
 * @Version V1.0
 **/
@Data
public class PurchasePartsDetailResult extends GeneralResult {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "部件id")
    private Long partsId;

    @ApiModelProperty(value = "部件名称(英文名称)")
    private String partsName;

    @ApiModelProperty(value = "部件编号")
    private String partsNo;

    @ApiModelProperty(value = "部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "采购单价")
    private BigDecimal unitPrice;

}
