package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author assert
 * @date 2021/1/22 10:28
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeProductionPurchaseOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeProductionPurchaseOrder {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 采购单号
     */
    @ApiModelProperty(value = "采购单号")
    private String purchaseNo;

    /**
     * 采购状态，1：草稿，10：采购中，20：待入库，30：部分入库，40：已入库，50：已完成，60：已取消
     */
    @ApiModelProperty(value = "采购状态，1：草稿，10：采购中，20：待入库，30：部分入库，40：已入库，50：已完成，60：已取消")
    private Integer purchaseStatus;

    /**
     * 采购数量
     */
    @ApiModelProperty(value = "采购数量")
    private Integer purchaseQty;

    /**
     * 采购总金额
     */
    @ApiModelProperty(value = "采购总金额")
    private BigDecimal purchaseAmount;

    /**
     * 工厂id
     */
    @ApiModelProperty(value = "工厂id")
    private Long factoryId;

    /**
     * 负责人id
     */
    @ApiModelProperty(value = "负责人id")
    private Long factoryPrincipalId;

    /**
     * 负责人名称
     */
    @ApiModelProperty(value = "负责人名称")
    private String factoryPrincipalName;

    /**
     * 负责人国家编码如+86
     */
    @ApiModelProperty(value = "负责人国家编码如+86")
    private String principalCountryCode;

    /**
     * 负责人电话
     */
    @ApiModelProperty(value = "负责人电话")
    private String principalTelephone;

    /**
     * 交货日期
     */
    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    /**
     * 采购对接人id
     */
    @ApiModelProperty(value = "采购对接人id")
    private Long dockingUser;

    /**
     * 采购对接人名称
     */
    @ApiModelProperty(value = "采购对接人名称")
    private String dockingUserName;

    /**
     * 采购对接人国家编码如+86
     */
    @ApiModelProperty(value = "采购对接人国家编码如+86")
    private String dockingCountryCode;

    /**
     * 采购对接人电话
     */
    @ApiModelProperty(value = "采购对接人电话")
    private String dockingUserTelephone;

    /**
     * 收货人id
     */
    @ApiModelProperty(value = "收货人id")
    private Long consigneeUser;

    /**
     * 收货人名称
     */
    @ApiModelProperty(value = "收货人名称")
    private String consigneeUserName;

    /**
     * 收货人国家编码如+86
     */
    @ApiModelProperty(value = "收货人国家编码如+86")
    private String consigneeCountryCode;

    /**
     * 收货人电话
     */
    @ApiModelProperty(value = "收货人电话")
    private String consigneeUserTelephone;

    /**
     * 收获地址
     */
    @ApiModelProperty(value = "收获地址")
    private String consigneeAddress;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 支付方式，1：月结，2：预付款，3：全款
     */
    @ApiModelProperty(value = "支付方式，1：月结，2：预付款，3：全款")
    private Integer paymentType;

    /**
     * 预计付款时间
     */
    @ApiModelProperty(value = "预计付款时间")
    private Date plannedPaymentTime;

    /**
     * 付款周期
     */
    @ApiModelProperty(value = "付款周期")
    private Integer paymentDay;

    /**
     * 实际付款时间
     */
    @ApiModelProperty(value = "实际付款时间")
    private Date paymentTime;

    /**
     * 采购合同
     */
    @ApiModelProperty(value = "采购合同")
    private String purchaseContract;

    /**
     * 金额类型，1：百分比，2：金额
     */
    @ApiModelProperty(value = "金额类型，1：百分比，2：金额")
    private Integer amountType;

    /**
     * 预付款比例(小数点最多一位)
     */
    @ApiModelProperty(value = "预付款比例(小数点最多一位)")
    private BigDecimal prePayRatio;

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

    public static OpeProductionPurchaseOrderBuilder builder() {
        return new OpeProductionPurchaseOrderBuilder();
    }
}