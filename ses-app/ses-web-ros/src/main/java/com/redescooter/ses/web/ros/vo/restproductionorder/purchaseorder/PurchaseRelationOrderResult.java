package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNamePurchaseRelationOrderResult
 * @Description
 * @Author Aleks
 * @Date2020/10/28 19:16
 * @Version V1.0
 **/
@Data
@ApiModel(value = "采购单关联的单据",description = "采购单关联的单据")
public class PurchaseRelationOrderResult {

    @ApiModelProperty("单据类型，1：调拨单，2：发货单，3：委托单")
    private Integer orderType;

    @ApiModelProperty("单号")
    private String orderNo;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("id")
    private Long id;
}
