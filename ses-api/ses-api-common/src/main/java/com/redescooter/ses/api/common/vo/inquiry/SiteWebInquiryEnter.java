package com.redescooter.ses.api.common.vo.inquiry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/27 16:04
 */
@Data
public class SiteWebInquiryEnter {

    @ApiModelProperty(value = "主建")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long customerId;

    /**
     * 客户来源渠道 官网/email/电话
     */
    @ApiModelProperty(value = "客户来源渠道 官网/email/电话")
    private String customerSource;

    /**
     * 销售员id
     */
    @ApiModelProperty(value = "销售员id")
    private Long salesId;

    /**
     * 1新建，2待支付，3进行中，4取消，5已完成，6关闭
     */
    @ApiModelProperty(value = "1新建，2待支付，3进行中，4取消，5已完成，6关闭")
    private Integer status;

    /**
     * 订单类型，1销售，2租赁
     */
    @ApiModelProperty(value = "订单类型，1销售，2租赁")
    private Integer orderType;

    /**
     * 产品Id
     */
    @ApiModelProperty(value = "产品Id ")
    private Long productId;

    /**
     * 颜色主建
     */
    @ApiModelProperty(value = "颜色主建")
    private Long colourId;

    /**
     * 产品颜色
     */
    @ApiModelProperty(value = "产品颜色 ")
    private String productColour;

    /**
     * 客户名字
     */
    @ApiModelProperty(value = "客户名字")
    private String fullName;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 国家名称
     */
    @ApiModelProperty(value = "国家名称")
    private String countryName;

    /**
     * 城市名称
     */
    @ApiModelProperty(value = "城市名称")
    private String cityName;

    /**
     * 区域邮编
     */
    @ApiModelProperty(value = "区域邮编")
    private String postcode;

    /**
     * 客户地址
     */
    @ApiModelProperty(value = "客户地址")
    private String address;

    /**
     * 提货方式
     */
    @ApiModelProperty(value = "提货方式")
    private Integer deliveryType;

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

    /**
     * 需求车辆数
     */
    @ApiModelProperty(value = "需求车辆数")
    private Integer scooterQuantity;

    /**
     * 预计交货时间
     */
    @ApiModelProperty(value = "预计交货时间")
    private Date etdDeliveryTime;

    /**
     * 是否同步
     */
    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

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


}
