package com.redescooter.ses.web.website.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
    * 订单支付记录表
    */
@ApiModel(value="com-redescooter-ses-web-website-dm-SitePaymentRecords")
@Data
@TableName(value = "site_payment_records")
public class SitePaymentRecords implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 状态,1正常，-1失效
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态,1正常，-1失效")
    private Integer status;

    /**
     * 订单主建
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value="订单主建")
    private Long orderId;

    /**
     * 客户主建
     */
    @TableField(value = "customerId")
    @ApiModelProperty(value="客户主建")
    private Long customerid;

    /**
     * 待付款金额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value="待付款金额")
    private BigDecimal amount;

    /**
     * 待付款金额
     */
    @TableField(value = "amount_obligation")
    @ApiModelProperty(value="待付款金额")
    private BigDecimal amountObligation;

    /**
     * Stripe三方香烟json
     */
    @TableField(value = "stripe_json")
    @ApiModelProperty(value="Stripe三方香烟json")
    private String stripeJson;

    /**
     * 是否同步
     */
    @TableField(value = "synchronize_flag")
    @ApiModelProperty(value="是否同步")
    private Boolean synchronizeFlag;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value="乐观锁")
    private Integer revision;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段")
    private BigDecimal def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_CUSTOMERID = "customerId";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_AMOUNT_OBLIGATION = "amount_obligation";

    public static final String COL_STRIPE_JSON = "stripe_json";

    public static final String COL_SYNCHRONIZE_FLAG = "synchronize_flag";

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
