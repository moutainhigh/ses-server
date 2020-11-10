package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
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
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductionPurchasDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "采购人对接人Id")
    private Long purchaserId;

    @ApiModelProperty(value = "采购人对接人名称")
    private String purchaserFirstName;

    @ApiModelProperty(value = "采购人对接人名称")
    private String purchaserLastName;

    @ApiModelProperty(value = "采购人对接人国家代码")
    private String purchaserCountryCode;

    @ApiModelProperty(value = "采购人对接人电话")
    private String purchaserTelephone;

    @ApiModelProperty(value = "负责人Id")
    private Long dockingId;

    @ApiModelProperty(value = "负责人名称")
    private String dockingFirstName;

    @ApiModelProperty(value = "负责人名称")
    private String dockingLastName;

    @ApiModelProperty(value = "负责人国家代码")
    private String dockingCountryCode;

    @ApiModelProperty(value = "负责人电话")
    private String dockingTelephone;

    @ApiModelProperty(value = "收货人Id")
    private Long receiverId;

    @ApiModelProperty(value = "收货人名称")
    private String receiverFirstName;

    @ApiModelProperty(value = "收货人名称")
    private String receiverLastName;

    @ApiModelProperty(value = "收货人国家代码")
    private String receiverCountryCode;

    @ApiModelProperty(value = "收货人电话")
    private String receiverTelephone;

    @ApiModelProperty(value = "收货地址")
    private String receiverAddress;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "采购产品")
    List<PurchasDetailProductListResult> productList;

    @ApiModelProperty(value = "关联订单")
    List<AssociatedOrderResult> associatedOrderResultList;
}
