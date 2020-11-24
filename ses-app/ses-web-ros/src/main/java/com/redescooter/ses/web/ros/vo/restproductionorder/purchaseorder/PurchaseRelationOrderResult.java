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

    @ApiModelProperty("单据类型，1：调拨单，3：发货单，5：委托单,6:物流运输单，7：工厂采购单，8：入库单，9：组装单")
    private Integer orderType;

    @ApiModelProperty("单号")
    private String orderNo;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("id")
    private Long id;
}
