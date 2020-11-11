package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassName:ProductionPurchasDetailResult
 * @description: ProductionPurchasDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 14:54 
 */
@ApiModel(value = "Production采购单详情出参", description = "Production采购单详情出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductionPurchasDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String purchaseNo;

    @ApiModelProperty(value = "状态")
    private Integer purchaseStatus;

    @ApiModelProperty(value = "采购人对接人Id")
    private Long dockingUser;

    @ApiModelProperty(value = "采购人对接人名称")
    private String dockingUserName;

    @ApiModelProperty(value = "采购人对接人国家代码")
    private String dockingCountryCode;

    @ApiModelProperty(value = "采购人对接人电话")
    private String dockingUserTelephone;

    @ApiModelProperty(value = "供应商Id")
    private Long factoryId;

    @ApiModelProperty(value = "供应商名称")
    private String factoryName;

    @ApiModelProperty(value = "负责人Id")
    private Long factoryPrincipalId;

    @ApiModelProperty(value = "负责人名称")
    private String factoryPrincipalName;

    @ApiModelProperty(value = "负责人国家代码")
    private String principalCountryCode;

    @ApiModelProperty(value = "负责人电话")
    private String principalTelephone;

    @ApiModelProperty(value = "收货人Id")
    private Long consigneeUser;

    @ApiModelProperty(value = "收货人名称")
    private String consigneeUserName;

    @ApiModelProperty(value = "收货人国家代码")
    private String consigneeCountryCode;

    @ApiModelProperty(value = "收货人电话")
    private String consigneeUserTelephone;

    @ApiModelProperty(value = "收货地址")
    private String consigneeAddress;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "采购产品")
    private List<PurchasDetailProductListResult> productList;

    @ApiModelProperty(value = "关联订单")
    private List<AssociatedOrderResult> associatedOrderResultList;

    @ApiModelProperty(value = "操作日志")
    private List<OpTraceResult> operatingDynamicsList;
}