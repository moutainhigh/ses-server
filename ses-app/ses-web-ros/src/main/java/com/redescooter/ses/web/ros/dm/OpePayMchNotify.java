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
 * 商用户支付通知表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpePayMchNotify")
@Data
@TableName(value = "ope_pay_mch_notify")
public class OpePayMchNotify implements Serializable {
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
     * 订单ID
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value = "订单ID")
    private String orderId;

    /**
     * 商用户ID
     */
    @TableField(value = "mch_id")
    @ApiModelProperty(value = "商用户ID")
    private String mchId;

    /**
     * 商用户订单号
     */
    @TableField(value = "mch_order_no")
    @ApiModelProperty(value = "商用户订单号")
    private String mchOrderNo;

    /**
     * 订单类型:1-支付,2-转账,3-退款
     */
    @TableField(value = "order_type")
    @ApiModelProperty(value = "订单类型:1-支付,2-转账,3-退款")
    private String orderType;

    /**
     * 通知地址
     */
    @TableField(value = "webhook_Url")
    @ApiModelProperty(value = "通知地址")
    private String webhookUrl;

    /**
     * 通知响应结果
     */
    @TableField(value = "result")
    @ApiModelProperty(value = "通知响应结果")
    private String result;

    /**
     * 通知状态,1-通知中,2-通知成功,3-通知失败
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "通知状态,1-通知中,2-通知成功,3-通知失败")
    private Byte status;

    /**
     * 最后一次通知时间
     */
    @TableField(value = "LastNotifyTime")
    @ApiModelProperty(value = "最后一次通知时间")
    private Date lastnotifytime;

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

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_MCH_ID = "mch_id";

    public static final String COL_MCH_ORDER_NO = "mch_order_no";

    public static final String COL_ORDER_TYPE = "order_type";

    public static final String COL_WEBHOOK_URL = "webhook_Url";

    public static final String COL_RESULT = "result";

    public static final String COL_STATUS = "status";

    public static final String COL_LASTNOTIFYTIME = "LastNotifyTime";

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