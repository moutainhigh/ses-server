package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 退款订单表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeRefundOrder")
@Data
@TableName(value = "ope_refund_order")
public class OpeRefundOrder implements Serializable {
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
     * 退款订单号
     */
    @TableField(value = "refund_order_id")
    @ApiModelProperty(value = "退款订单号")
    private Long refundOrderId;

    /**
     * 支付订单号
     */
    @TableField(value = "pay_order_id")
    @ApiModelProperty(value = "支付订单号")
    private Long payOrderId;

    /**
     * 渠道支付单号
     */
    @TableField(value = "channel_pay_order_no")
    @ApiModelProperty(value = "渠道支付单号")
    private String channelPayOrderNo;

    /**
     * 商用户ID
     */
    @TableField(value = "mch_id")
    @ApiModelProperty(value = "商用户ID")
    private Long mchId;

    /**
     * 商用户退款单号
     */
    @TableField(value = "mch_refund_no")
    @ApiModelProperty(value = "商用户退款单号")
    private String mchRefundNo;

    /**
     * 渠道ID
     */
    @TableField(value = "channel_id")
    @ApiModelProperty(value = "渠道ID")
    private String channelId;

    /**
     * 支付金额,单位分
     */
    @TableField(value = "pay_amount")
    @ApiModelProperty(value = "支付金额,单位分")
    private Long payAmount;

    /**
     * 退款金额,单位分
     */
    @TableField(value = "refund_amount")
    @ApiModelProperty(value = "退款金额,单位分")
    private Long refundAmount;

    /**
     * 三位货币代码,人民币:cny
     */
    @TableField(value = "currency")
    @ApiModelProperty(value = "三位货币代码,人民币:cny")
    private String currency;

    /**
     * 退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成")
    private Byte status;

    /**
     * 退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败
     */
    @TableField(value = "result")
    @ApiModelProperty(value = "退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败")
    private Byte result;

    /**
     * 客户端IP
     */
    @TableField(value = "client_ip")
    @ApiModelProperty(value = "客户端IP")
    private String clientIp;

    /**
     * 设备
     */
    @TableField(value = "device_id")
    @ApiModelProperty(value = "设备")
    private Long deviceId;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 用户姓名
     */
    @TableField(value = "userName")
    @ApiModelProperty(value = "用户姓名")
    private String username;

    /**
     * 通知地址
     */
    @TableField(value = "webhook_Url")
    @ApiModelProperty(value = "通知地址")
    private String webhookUrl;

    /**
     * 订单失效时间
     */
    @TableField(value = "pay_fail_time")
    @ApiModelProperty(value = "订单失效时间")
    private Long payFailTime;

    /**
     * 订单支付成功时间
     */
    @TableField(value = "pay_succeed_time")
    @ApiModelProperty(value = "订单支付成功时间")
    private Long paySucceedTime;

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

    public static final String COL_REFUND_ORDER_ID = "refund_order_id";

    public static final String COL_PAY_ORDER_ID = "pay_order_id";

    public static final String COL_CHANNEL_PAY_ORDER_NO = "channel_pay_order_no";

    public static final String COL_MCH_ID = "mch_id";

    public static final String COL_MCH_REFUND_NO = "mch_refund_no";

    public static final String COL_CHANNEL_ID = "channel_id";

    public static final String COL_PAY_AMOUNT = "pay_amount";

    public static final String COL_REFUND_AMOUNT = "refund_amount";

    public static final String COL_CURRENCY = "currency";

    public static final String COL_STATUS = "status";

    public static final String COL_RESULT = "result";

    public static final String COL_CLIENT_IP = "client_ip";

    public static final String COL_DEVICE_ID = "device_id";

    public static final String COL_REMARK = "remark";

    public static final String COL_USERNAME = "userName";

    public static final String COL_WEBHOOK_URL = "webhook_Url";

    public static final String COL_PAY_FAIL_TIME = "pay_fail_time";

    public static final String COL_PAY_SUCCEED_TIME = "pay_succeed_time";

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
