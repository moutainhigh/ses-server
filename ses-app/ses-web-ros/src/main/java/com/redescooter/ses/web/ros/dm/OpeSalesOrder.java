package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSalesOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_sales_order")
public class OpeSalesOrder implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 客户ID
     */
    @TableField(value = "customer_id")
    @ApiModelProperty(value = "客户ID")
    private Long customerId;

    /**
     * 客户姓名
     */
    @TableField(value = "customer_name")
    @ApiModelProperty(value = "客户姓名")
    private String customerName;

    /**
     * 客户地址
     */
    @TableField(value = "customer_address")
    @ApiModelProperty(value = "客户地址")
    private String customerAddress;

    /**
     * 客户电话
     */
    @TableField(value = "customer_phone")
    @ApiModelProperty(value = "客户电话")
    private String customerPhone;

    /**
     * 客户邮箱
     */
    @TableField(value = "customer_email")
    @ApiModelProperty(value = "客户邮箱")
    private String customerEmail;

    /**
     * 客户经度
     */
    @TableField(value = "customer_longitude")
    @ApiModelProperty(value = "客户经度")
    private BigDecimal customerLongitude;

    /**
     * 客户纬度
     */
    @TableField(value = "customer_latitude")
    @ApiModelProperty(value = "客户纬度")
    private BigDecimal customerLatitude;

    /**
     * 订单类型(车辆/配件) 等价于 product_type表 code字段
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "订单类型(车辆/配件) 等价于 product_type表 code字段")
    private String type;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 订单状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "订单状态")
    private String status;

    /**
     * 支付状态
     */
    @TableField(value = "payment_status")
    @ApiModelProperty(value = "支付状态")
    private String paymentStatus;

    /**
     * 付款方式
     */
    @TableField(value = "payment_method")
    @ApiModelProperty(value = "付款方式")
    private String paymentMethod;

    /**
     * 总金额
     */
    @TableField(value = "total_amount")
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount;

    /**
     * 实际应该支付的总金额
     */
    @TableField(value = "actual_payment_amount")
    @ApiModelProperty(value = "实际应该支付的总金额")
    private BigDecimal actualPaymentAmount;

    /**
     * 已支付的金额
     */
    @TableField(value = "paid_amount")
    @ApiModelProperty(value = "已支付的金额")
    private BigDecimal paidAmount;

    /**
     * 对应的销售
     */
    @TableField(value = "sales_id")
    @ApiModelProperty(value = "对应的销售")
    private String salesId;

    /**
     * 数量
     */
    @TableField(value = "quantity")
    @ApiModelProperty(value = "数量")
    private Integer quantity;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 发货时间
     */
    @TableField(value = "delivery_time")
    @ApiModelProperty(value = "发货时间")
    private Date deliveryTime;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 申请发货时间
     */
    @TableField(value = "apply_delivery_time")
    @ApiModelProperty(value = "申请发货时间")
    private Date applyDeliveryTime;

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

    public static final String COL_CUSTOMER_ID = "customer_id";

    public static final String COL_CUSTOMER_NAME = "customer_name";

    public static final String COL_CUSTOMER_ADDRESS = "customer_address";

    public static final String COL_CUSTOMER_PHONE = "customer_phone";

    public static final String COL_CUSTOMER_EMAIL = "customer_email";

    public static final String COL_CUSTOMER_LONGITUDE = "customer_longitude";

    public static final String COL_CUSTOMER_LATITUDE = "customer_latitude";

    public static final String COL_TYPE = "type";

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_STATUS = "status";

    public static final String COL_PAYMENT_STATUS = "payment_status";

    public static final String COL_PAYMENT_METHOD = "payment_method";

    public static final String COL_TOTAL_AMOUNT = "total_amount";

    public static final String COL_ACTUAL_PAYMENT_AMOUNT = "actual_payment_amount";

    public static final String COL_PAID_AMOUNT = "paid_amount";

    public static final String COL_SALES_ID = "sales_id";

    public static final String COL_QUANTITY = "quantity";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_DELIVERY_TIME = "delivery_time";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_APPLY_DELIVERY_TIME = "apply_delivery_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}