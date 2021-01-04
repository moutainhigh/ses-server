package com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseRelationOrderResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassNameInWhouseDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/11/11 14:20
 * @Version V1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "入库单详情接口出参",description = "入库单详情接口出参")
public class InWhouseDetailResult extends GeneralResult {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty(value="入库单号")
    private String inWhNo;

    @ApiModelProperty(value="入库单状态， 1： 草稿，:10：待质检，20：质检中，25：待入库，30：已入库")
    private Integer inWhStatus;

    @ApiModelProperty("入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他")
    private Integer inWhType;

    @ApiModelProperty("入库单据类型,1:车辆，2:组装件，3:部件")
    private Integer orderType;

    @ApiModelProperty(value = "关联的单据id")
    private Long relationOrderId;

    @ApiModelProperty(value = "关联的单据号")
    private String relationOrderNo;

    @ApiModelProperty(value = "关联的单据类型，7：生产采购单，9：组装单``")
    private Integer relationOrderType;

    @ApiModelProperty(value = "入库仓库。1:成品库，2:原料库，3:不合格品库")
    private Integer whType;

    @ApiModelProperty("部件明细")
    private List<InWhouseDetailPartsResult>  parts;

    @ApiModelProperty("整车明细")
    private List<InWhouseDetailScooterResult>  scooters;

    @ApiModelProperty("组装件明细")
    private List<InWhouseDetailCombinResult>  combins;

    @ApiModelProperty("操作动态")
    private List<OpTraceResult> opTraces;

    @ApiModelProperty("关联的单据")
    private List<PurchaseRelationOrderResult> relationOrders;


}
