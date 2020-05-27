package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付订单表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpePayOrder")
@Data
@TableName(value = "ope_pay_order")
public class OpePayOrder implements Serializable {
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
     * 商用户订单号
     */
    @TableField(value = "mchOrder_no")
    @ApiModelProperty(value = "商用户订单号")
    private String mchorderNo;

    /**
     * 渠道ID
     */
    @TableField(value = "channel_id")
    @ApiModelProperty(value = "渠道ID")
    private Long channelId;

    /**
     * 支付金额,单位分
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "支付金额,单位分")
    private Long amount;

    /**
     * 数量
     */
    @TableField(value = "qty")
    @ApiModelProperty(value = "数量")
    private Integer qty;

    /**
     * FR
     */
    @TableField(value = "country")
    @ApiModelProperty(value = "FR")
    private String country;

    /**
     * 三位货币代码,欧元:eur
     */
    @TableField(value = "currency")
    @ApiModelProperty(value = "三位货币代码,欧元:eur")
    private String currency;

    /**
     * 支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成")
    private Byte status;

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
     * 支付产品编号
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "支付产品编号")
    private Long productId;

    /**
     * 支付产品名称
     */
    @TableField(value = "product_name")
    @ApiModelProperty(value = "支付产品名称")
    private String productName;

    /**
     * 产品标题
     */
    @TableField(value = "subject")
    @ApiModelProperty(value = "产品标题")
    private String subject;

    /**
     * 产品描述信息
     */
    @TableField(value = "body")
    @ApiModelProperty(value = "产品描述信息")
    private String body;

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

    public static final String COL_PAY_ORDER_ID = "pay_order_id";

    public static final String COL_MCH_ID = "mch_id";

    public static final String COL_MCHORDER_NO = "mchOrder_no";

    public static final String COL_CHANNEL_ID = "channel_id";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_QTY = "qty";

    public static final String COL_COUNTRY = "country";

    public static final String COL_CURRENCY = "currency";

    public static final String COL_STATUS = "status";

    public static final String COL_CLIENT_IP = "client_ip";

    public static final String COL_DEVICE_ID = "device_id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_PRODUCT_NAME = "product_name";

    public static final String COL_SUBJECT = "subject";

    public static final String COL_BODY = "body";

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