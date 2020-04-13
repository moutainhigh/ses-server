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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSalesOrderPayment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_sales_order_payment")
public class OpeSalesOrderPayment implements Serializable {
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
     * 销售订单ID
     */
    @TableField(value = "sales_order_id")
    @ApiModelProperty(value = "销售订单ID")
    private Integer salesOrderId;

    /**
     * 付款人Id 等价于 staff_id
     */
    @TableField(value = "payer_id")
    @ApiModelProperty(value = "付款人Id 等价于 staff_id")
    private Long payerId;

    /**
     * CASH;CARD;CHEQUE
     */
    @TableField(value = "payment_type")
    @ApiModelProperty(value = "CASH;CARD;CHEQUE")
    private String paymentType;

    @TableField(value = "planned_payment_time")
    @ApiModelProperty(value = "")
    private Date plannedPaymentTime;

    @TableField(value = "payment_time")
    @ApiModelProperty(value = "")
    private Date paymentTime;

    /**
     * 支付状态:Paid Unpaid
     */
    @TableField(value = "payment_status")
    @ApiModelProperty(value = "支付状态:Paid Unpaid")
    private String paymentStatus;

    @TableField(value = "description")
    @ApiModelProperty(value = "")
    private String description;

    @TableField(value = "amount")
    @ApiModelProperty(value = "")
    private BigDecimal amount;

    /**
     * 55% 标示为 55
     */
    @TableField(value = "amount_proportion")
    @ApiModelProperty(value = "55% 标示为 55")
    private Integer amountProportion;

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

    public static final String COL_SALES_ORDER_ID = "sales_order_id";

    public static final String COL_PAYER_ID = "payer_id";

    public static final String COL_PAYMENT_TYPE = "payment_type";

    public static final String COL_PLANNED_PAYMENT_TIME = "planned_payment_time";

    public static final String COL_PAYMENT_TIME = "payment_time";

    public static final String COL_PAYMENT_STATUS = "payment_status";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_AMOUNT_PROPORTION = "amount_proportion";

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