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

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeAssemblyOrderPayment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_assembly_order_payment")
public class OpeAssemblyOrderPayment {
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
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    @TableLogic
    private Integer dr;

    /**
     * 租户Id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    /**
     * userId
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "userId")
    private Long userId;

    /**
     * 组装单主键
     */
    @TableField(value = "ope_assembly_order_id")
    @ApiModelProperty(value = "组装单主键")
    private Long opeAssemblyOrderId;

    /**
     * 分期，月结
     */
    @TableField(value = "payment_type")
    @ApiModelProperty(value = "分期，月结")
    private String paymentType;

    /**
     * 预计付款时间
     */
    @TableField(value = "planned_payment_time")
    @ApiModelProperty(value = "预计付款时间")
    private Date plannedPaymentTime;

    /**
     * 付款周期
     */
    @TableField(value = "payment_day")
    @ApiModelProperty(value = "付款周期")
    private Integer paymentDay;

    /**
     * 实际付款时间
     */
    @TableField(value = "payment_time")
    @ApiModelProperty(value = "实际付款时间")
    private Date paymentTime;

    /**
     * 支付状态:Paid Unpaid
     */
    @TableField(value = "payment_status")
    @ApiModelProperty(value = "支付状态:Paid Unpaid")
    private String paymentStatus;

    /**
     * 支付优先级
     */
    @TableField(value = "payment_priority")
    @ApiModelProperty(value = "支付优先级")
    private Integer paymentPriority;

    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 价格
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "价格")
    private BigDecimal amount;

    /**
     * 55% 标示为 55
     */
    @TableField(value = "amount_proportion")
    @ApiModelProperty(value = "55% 标示为 55")
    private Integer amountProportion;

    /**
     * 发票单号
     */
    @TableField(value = "invoice_num")
    @ApiModelProperty(value = "发票单号")
    private String invoiceNum;

    /**
     * 发票附件
     */
    @TableField(value = "invoice_picture")
    @ApiModelProperty(value = "发票附件")
    private String invoicePicture;

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

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_OPE_ASSEMBLY_ORDER_ID = "ope_assembly_order_id";

    public static final String COL_PAYMENT_TYPE = "payment_type";

    public static final String COL_PLANNED_PAYMENT_TIME = "planned_payment_time";

    public static final String COL_PAYMENT_DAY = "payment_day";

    public static final String COL_PAYMENT_TIME = "payment_time";

    public static final String COL_PAYMENT_STATUS = "payment_status";

    public static final String COL_PAYMENT_PRIORITY = "payment_priority";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_AMOUNT_PROPORTION = "amount_proportion";

    public static final String COL_INVOICE_NUM = "invoice_num";

    public static final String COL_INVOICE_PICTURE = "invoice_picture";

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