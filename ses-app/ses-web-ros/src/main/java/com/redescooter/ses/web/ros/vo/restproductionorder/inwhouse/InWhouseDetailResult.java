package com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseRelationOrderResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassNameInWhouseDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/11/11 14:20
 * @Version V1.0
 **/
@Data
@ApiModel(value = "入库单详情接口出参",description = "入库单详情接口出参")
public class InWhouseDetailResult extends GeneralResult {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他")
    private Integer inWhType;

    @ApiModelProperty("入库单据类型,1:车辆，2:组装件，3:部件")
    private Integer orderType;

    @ApiModelProperty("部件明细")
    private List<InWhouseDetailPartsResult>  parts;

    @ApiModelProperty("整车明细")
    private List<InWhouseDetailScooterResult>  scooters;

    @ApiModelProperty("组装件明细")
    private List<InWhouseDetailCombinResult>  combins;

    // todo 枚举待调整
    @ApiModelProperty("关联的单据")
    private List<PurchaseRelationOrderResult> relationOrders;


}
