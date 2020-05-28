package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付凭据表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpePayReceipt")
@Data
@TableName(value = "ope_pay_receipt")
public class OpePayReceipt implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "删除标识")
    @TableLogic
    private Integer dr;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * userid
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "userid")
    private Long userId;

    /**
     * 支付订单号
     */
    @TableField(value = "pay_order_id")
    @ApiModelProperty(value = "支付订单号")
    private Long payOrderId;

    /**
     * 商用户ID
     */
    @TableField(value = "mch_id")
    @ApiModelProperty(value = "商用户ID")
    private Long mchId;

    /**
     * IAP业务号
     */
    @TableField(value = "transaction_id")
    @ApiModelProperty(value = "IAP业务号")
    private String transactionId;

    /**
     * 渠道数据
     */
    @TableField(value = "receipt_data")
    @ApiModelProperty(value = "渠道数据")
    private String receiptData;

    /**
     * 处理状态:0-未处理,1-处理成功,-1-处理失败
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "处理状态:0-未处理,1-处理成功,-1-处理失败")
    private Byte status;

    /**
     * 处理次数
     */
    @TableField(value = "handle_count")
    @ApiModelProperty(value = "处理次数")
    private Byte handleCount;

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

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_PAY_ORDER_ID = "pay_order_id";

    public static final String COL_MCH_ID = "mch_id";

    public static final String COL_TRANSACTION_ID = "transaction_id";

    public static final String COL_RECEIPT_DATA = "receipt_data";

    public static final String COL_STATUS = "status";

    public static final String COL_HANDLE_COUNT = "handle_count";

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
