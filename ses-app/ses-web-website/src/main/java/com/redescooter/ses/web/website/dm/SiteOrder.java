package com.redescooter.ses.web.website.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 销售订单
 */
@ApiModel(value = "com-redescooter-ses-web-website-dm-SiteOrder")
@Data
@TableName(value = "site_order")
public class SiteOrder implements Serializable {
    /**
     * 主建
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主建")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 1新建，2待支付，3进行中，4取消，5已完成，6关闭
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "1新建，2待支付，3进行中，4取消，5已完成，6关闭")
    private Integer status;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 客户id
     */
    @TableField(value = "customer_id")
    @ApiModelProperty(value = "客户id")
    private Long customerId;

    /**
     * 客户来源渠道 官网/email/电话
     */
    @TableField(value = "customer_source")
    @ApiModelProperty(value = "客户来源渠道 官网/email/电话")
    private String customerSource;

    /**
     * 销售员id
     */
    @TableField(value = "sales_id")
    @ApiModelProperty(value = "销售员id")
    private Long salesId;

    /**
     * 订单类型，1销售，2租赁
     */
    @TableField(value = "order_type")
    @ApiModelProperty(value = "订单类型，1销售，2租赁")
    private Integer orderType;

    /**
     * 产品Id
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品Id ")
    private Long productId;

    /**
     * 颜色主建
     */
    @TableField(value = "colour_id")
    @ApiModelProperty(value = "颜色主建")
    private Long colourId;

    /**
     * 产品颜色
     */
    @TableField(value = "product_colour")
    @ApiModelProperty(value = "产品颜色 ")
    private String productColour;

    /**
     * 客户名字
     */
    @TableField(value = "full_name")
    @ApiModelProperty(value = "客户名字")
    private String fullName;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 国家名称
     */
    @TableField(value = "country_name")
    @ApiModelProperty(value = "国家名称")
    private String countryName;

    /**
     * 城市名称
     */
    @TableField(value = "city_name")
    @ApiModelProperty(value = "城市名称")
    private String cityName;

    /**
     * 区域邮编
     */
    @TableField(value = "postcode")
    @ApiModelProperty(value = "区域邮编")
    private String postcode;

    /**
     * 客户地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "客户地址")
    private String address;

    /**
     * 提货方式
     */
    @TableField(value = "delivery_type")
    @ApiModelProperty(value = "提货方式")
    private Integer deliveryType;

    /**
     * 取货门店，如果取货类型是到门店取货，那么id是门店ID
     */
    @TableField(value = "dealer_id")
    @ApiModelProperty(value = "取货门店，如果取货类型是到门店取货，那么id是门店ID")
    private Long dealerId;

    /**
     * 购买电池数
     */
    @TableField(value = "battery_qty")
    @ApiModelProperty(value = "购买电池数")
    private Integer batteryQty;

    /**
     * 运费
     */
    @TableField(value = "freight")
    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    /**
     * 产品单价
     */
    @TableField(value = "product_price")
    @ApiModelProperty(value = "产品单价")
    private BigDecimal productPrice;

    /**
     * 单据总价
     */
    @TableField(value = "total_price")
    @ApiModelProperty(value = "单据总价")
    private BigDecimal totalPrice;

    /**
     * 已付金额
     */
    @TableField(value = "amount_paid")
    @ApiModelProperty(value = "已付金额")
    private BigDecimal amountPaid;

    /**
     * 待付款金额
     */
    @TableField(value = "amount_obligation")
    @ApiModelProperty(value = "待付款金额")
    private BigDecimal amountObligation;

    /**
     * 预付定金
     */
    @TableField(value = "prepaid_deposit")
    @ApiModelProperty(value = "预付定金")
    private BigDecimal prepaidDeposit;

    /**
     * 优惠抵扣金额
     */
    @TableField(value = "amount_discount")
    @ApiModelProperty(value = "优惠抵扣金额")
    private BigDecimal amountDiscount;

    /**
     * 支付方式
     */
    @TableField(value = "payment_type_id")
    @ApiModelProperty(value = "支付方式")
    private Long paymentTypeId;

    /**
     * 支付状态,1待支付，2部分支付，3已支付
     */
    @TableField(value = "pay_status")
    @ApiModelProperty(value = "支付状态,1待支付，2部分支付，3已支付")
    private Integer payStatus;

    /**
     * 需求车辆数
     */
    @TableField(value = "scooter_quantity")
    @ApiModelProperty(value = "需求车辆数")
    private Integer scooterQuantity;

    /**
     * 预计交货时间
     */
    @TableField(value = "etd_delivery_time")
    @ApiModelProperty(value = "预计交货时间")
    private Date etdDeliveryTime;

    /**
     * 是否同步
     */
    @TableField(value = "synchronize_flag")
    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_CUSTOMER_ID = "customer_id";

    public static final String COL_CUSTOMER_SOURCE = "customer_source";

    public static final String COL_SALES_ID = "sales_id";

    public static final String COL_ORDER_TYPE = "order_type";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_COLOUR_ID = "colour_id";

    public static final String COL_PRODUCT_COLOUR = "product_colour";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_COMPANY_NAME = "company_name";

    public static final String COL_COUNTRY_NAME = "country_name";

    public static final String COL_CITY_NAME = "city_name";

    public static final String COL_POSTCODE = "postcode";

    public static final String COL_ADDRESS = "address";

    public static final String COL_DELIVERY_TYPE = "delivery_type";

    public static final String COL_DEALER_ID = "dealer_id";

    public static final String COL_BATTERY_QTY = "battery_qty";

    public static final String COL_FREIGHT = "freight";

    public static final String COL_PRODUCT_PRICE = "product_price";

    public static final String COL_TOTAL_PRICE = "total_price";

    public static final String COL_AMOUNT_PAID = "amount_paid";

    public static final String COL_AMOUNT_OBLIGATION = "amount_obligation";

    public static final String COL_PREPAID_DEPOSIT = "prepaid_deposit";

    public static final String COL_AMOUNT_DISCOUNT = "amount_discount";

    public static final String COL_PAYMENT_TYPE_ID = "payment_type_id";

    public static final String COL_PAY_STATUS = "pay_status";

    public static final String COL_SCOOTER_QUANTITY = "scooter_quantity";

    public static final String COL_ETD_DELIVERY_TIME = "etd_delivery_time";

    public static final String COL_SYNCHRONIZE_FLAG = "synchronize_flag";

    public static final String COL_REMARKS = "remarks";

    public static final String COL_REVISION = "revision";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}