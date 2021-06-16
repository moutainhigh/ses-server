package com.redescooter.ses.api.hub.vo.website;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@ApiModel(value = "支付同步ros", description = "支付同步ros")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SyncOrderDataEnter extends GeneralEnter {
    /**
     * 主建
     */
    @ApiModelProperty(value = "主建")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 1新建，2待支付，3进行中，4取消，5已完成，6关闭
     */
    @ApiModelProperty(value = "1新建，2待支付，3进行中，4取消，5已完成，6关闭")
    private Integer status;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 产品Id
     */
    @ApiModelProperty(value = "产品Id ")
    private Long productId;


    /**
     * 购买电池数
     */
    @ApiModelProperty(value = "购买电池数")
    private Integer batteryQty;

    /**
     * 运费
     */
    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    /**
     * 产品单价
     */
    @ApiModelProperty(value = "产品单价")
    private BigDecimal productPrice;

    /**
     * 单据总价
     */
    @ApiModelProperty(value = "单据总价")
    private BigDecimal totalPrice;

    /**
     * 已付金额
     */
    @ApiModelProperty(value = "已付金额")
    private BigDecimal amountPaid;

    /**
     * 待付款金额
     */
    @ApiModelProperty(value = "待付款金额")
    private BigDecimal amountObligation;

    /**
     * 预付定金
     */
    @ApiModelProperty(value = "预付定金")
    private BigDecimal prepaidDeposit;

    /**
     * 优惠抵扣金额
     */
    @ApiModelProperty(value = "优惠抵扣金额")
    private BigDecimal amountDiscount;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private Long paymentTypeId;

    /**
     * 支付状态,1待支付，2部分支付，3已支付
     */
    @ApiModelProperty(value = "支付状态,1待支付，2部分支付，3已支付")
    private Integer payStatus;

    @ApiModelProperty(value = "是否为分期")
    private String isInstallment;

    /**
     * 需求车辆数
     */
    @ApiModelProperty(value = "需求车辆数")
    private Integer scooterQuantity;



    /**
     * 是否同步
     */
    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

}
