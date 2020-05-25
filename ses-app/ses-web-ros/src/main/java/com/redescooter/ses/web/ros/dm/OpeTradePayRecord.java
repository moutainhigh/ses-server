package com.redescooter.ses.web.ros.dm;

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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeTradePayRecord")
@Data
@TableName(value = "ope_trade_pay_record")
public class OpeTradePayRecord implements Serializable {
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
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 状态(参考枚举:paymentrecordstatusenum)
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态(参考枚举:paymentrecordstatusenum)")
    private String status;

    /**
     * 修改者
     */
    @TableField(value = "editor")
    @ApiModelProperty(value = "修改者")
    private String editor;

    /**
     * 创建者
     */
    @TableField(value = "creater")
    @ApiModelProperty(value = "创建者")
    private String creater;

    /**
     * 最后修改时间
     */
    @TableField(value = "edit_time")
    @ApiModelProperty(value = "最后修改时间")
    private Date editTime;

    /**
     * 产品id
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品id")
    private Long productId;

    /**
     * 产品名称
     */
    @TableField(value = "product_name")
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 商户订单号
     */
    @TableField(value = "merchant_order_no")
    @ApiModelProperty(value = "商户订单号")
    private String merchantOrderNo;

    /**
     * 支付流水号
     */
    @TableField(value = "trx_no")
    @ApiModelProperty(value = "支付流水号")
    private String trxNo;

    /**
     * 银行订单号
     */
    @TableField(value = "bank_order_no")
    @ApiModelProperty(value = "银行订单号")
    private String bankOrderNo;

    /**
     * 银行流水号
     */
    @TableField(value = "bank_trx_no")
    @ApiModelProperty(value = "银行流水号")
    private String bankTrxNo;

    /**
     * 商用户ID
     */
    @TableField(value = "mch_id")
    @ApiModelProperty(value = "商用户ID")
    private Long mchId;

    /**
     * 付款人编号
     */
    @TableField(value = "payer_user_no")
    @ApiModelProperty(value = "付款人编号")
    private String payerUserNo;

    /**
     * 付款人名称
     */
    @TableField(value = "payer_name")
    @ApiModelProperty(value = "付款人名称")
    private String payerName;

    /**
     * 付款方支付金额
     */
    @TableField(value = "payer_pay_amount")
    @ApiModelProperty(value = "付款方支付金额")
    private BigDecimal payerPayAmount;

    /**
     * 付款方手续费
     */
    @TableField(value = "payer_fee")
    @ApiModelProperty(value = "付款方手续费")
    private BigDecimal payerFee;

    /**
     * 付款方账户类型(参考账户类型枚举:accounttypeenum)
     */
    @TableField(value = "payer_account_type")
    @ApiModelProperty(value = "付款方账户类型(参考账户类型枚举:accounttypeenum)")
    private String payerAccountType;

    /**
     * 收款人编号
     */
    @TableField(value = "receiver_user_no")
    @ApiModelProperty(value = "收款人编号")
    private String receiverUserNo;

    /**
     * 收款人名称
     */
    @TableField(value = "receiver_name")
    @ApiModelProperty(value = "收款人名称")
    private String receiverName;

    /**
     * 收款方支付金额
     */
    @TableField(value = "receiver_pay_amount")
    @ApiModelProperty(value = "收款方支付金额")
    private BigDecimal receiverPayAmount;

    /**
     * 收款方手续费
     */
    @TableField(value = "receiver_fee")
    @ApiModelProperty(value = "收款方手续费")
    private BigDecimal receiverFee;

    /**
     * 收款方账户类型(参考账户类型枚举:accounttypeenum)
     */
    @TableField(value = "receiver_account_type")
    @ApiModelProperty(value = "收款方账户类型(参考账户类型枚举:accounttypeenum)")
    private String receiverAccountType;

    /**
     * 下单ip(客户端ip,从网关中获取)
     */
    @TableField(value = "order_ip")
    @ApiModelProperty(value = "下单ip(客户端ip,从网关中获取)")
    private String orderIp;

    /**
     * 从哪个页面链接过来的(可用于防诈骗)
     */
    @TableField(value = "order_referer_url")
    @ApiModelProperty(value = "从哪个页面链接过来的(可用于防诈骗)")
    private String orderRefererUrl;

    /**
     * 订单金额
     */
    @TableField(value = "order_amount")
    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    /**
     * 费率
     */
    @TableField(value = "fee_rate")
    @ApiModelProperty(value = "费率")
    private BigDecimal feeRate;

    /**
     * 页面回调通知url
     */
    @TableField(value = "return_url")
    @ApiModelProperty(value = "页面回调通知url")
    private String returnUrl;

    /**
     * 后台异步通知url
     */
    @TableField(value = "notify_url")
    @ApiModelProperty(value = "后台异步通知url")
    private String notifyUrl;

    /**
     * 支付方式编号
     */
    @TableField(value = "pay_way_code")
    @ApiModelProperty(value = "支付方式编号")
    private String payWayCode;

    /**
     * 支付方式名称
     */
    @TableField(value = "pay_way_name")
    @ApiModelProperty(value = "支付方式名称")
    private String payWayName;

    /**
     * 支付成功时间
     */
    @TableField(value = "pay_success_time")
    @ApiModelProperty(value = "支付成功时间")
    private Date paySuccessTime;

    /**
     * 完成时间
     */
    @TableField(value = "complete_time")
    @ApiModelProperty(value = "完成时间")
    private Date completeTime;

    /**
     * 是否退款(100:是,101:否,默认值为:101)
     */
    @TableField(value = "is_refund")
    @ApiModelProperty(value = "是否退款(100:是,101:否,默认值为:101)")
    private String isRefund;

    /**
     * 退款次数(默认值为:0)
     */
    @TableField(value = "refund_times")
    @ApiModelProperty(value = "退款次数(默认值为:0)")
    private Integer refundTimes;

    /**
     * 成功退款总金额
     */
    @TableField(value = "success_refund_amount")
    @ApiModelProperty(value = "成功退款总金额")
    private BigDecimal successRefundAmount;

    /**
     * 交易业务类型 ：消费、充值等
     */
    @TableField(value = "trx_type")
    @ApiModelProperty(value = "交易业务类型  ：消费、充值等")
    private String trxType;

    /**
     * 订单来源
     */
    @TableField(value = "order_from")
    @ApiModelProperty(value = "订单来源")
    private String orderFrom;

    /**
     * 支付类型编号
     */
    @TableField(value = "pay_type_code")
    @ApiModelProperty(value = "支付类型编号")
    private String payTypeCode;

    /**
     * 支付类型名称
     */
    @TableField(value = "pay_type_name")
    @ApiModelProperty(value = "支付类型名称")
    private String payTypeName;

    /**
     * 资金流入类型
     */
    @TableField(value = "fund_into_type")
    @ApiModelProperty(value = "资金流入类型")
    private String fundIntoType;

    /**
     * 银行返回信息
     */
    @TableField(value = "bank_return_msg")
    @ApiModelProperty(value = "银行返回信息")
    private String bankReturnMsg;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

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

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_STATUS = "status";

    public static final String COL_EDITOR = "editor";

    public static final String COL_CREATER = "creater";

    public static final String COL_EDIT_TIME = "edit_time";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_PRODUCT_NAME = "product_name";

    public static final String COL_MERCHANT_ORDER_NO = "merchant_order_no";

    public static final String COL_TRX_NO = "trx_no";

    public static final String COL_BANK_ORDER_NO = "bank_order_no";

    public static final String COL_BANK_TRX_NO = "bank_trx_no";

    public static final String COL_MCH_ID = "mch_id";

    public static final String COL_PAYER_USER_NO = "payer_user_no";

    public static final String COL_PAYER_NAME = "payer_name";

    public static final String COL_PAYER_PAY_AMOUNT = "payer_pay_amount";

    public static final String COL_PAYER_FEE = "payer_fee";

    public static final String COL_PAYER_ACCOUNT_TYPE = "payer_account_type";

    public static final String COL_RECEIVER_USER_NO = "receiver_user_no";

    public static final String COL_RECEIVER_NAME = "receiver_name";

    public static final String COL_RECEIVER_PAY_AMOUNT = "receiver_pay_amount";

    public static final String COL_RECEIVER_FEE = "receiver_fee";

    public static final String COL_RECEIVER_ACCOUNT_TYPE = "receiver_account_type";

    public static final String COL_ORDER_IP = "order_ip";

    public static final String COL_ORDER_REFERER_URL = "order_referer_url";

    public static final String COL_ORDER_AMOUNT = "order_amount";

    public static final String COL_FEE_RATE = "fee_rate";

    public static final String COL_RETURN_URL = "return_url";

    public static final String COL_NOTIFY_URL = "notify_url";

    public static final String COL_PAY_WAY_CODE = "pay_way_code";

    public static final String COL_PAY_WAY_NAME = "pay_way_name";

    public static final String COL_PAY_SUCCESS_TIME = "pay_success_time";

    public static final String COL_COMPLETE_TIME = "complete_time";

    public static final String COL_IS_REFUND = "is_refund";

    public static final String COL_REFUND_TIMES = "refund_times";

    public static final String COL_SUCCESS_REFUND_AMOUNT = "success_refund_amount";

    public static final String COL_TRX_TYPE = "trx_type";

    public static final String COL_ORDER_FROM = "order_from";

    public static final String COL_PAY_TYPE_CODE = "pay_type_code";

    public static final String COL_PAY_TYPE_NAME = "pay_type_name";

    public static final String COL_FUND_INTO_TYPE = "fund_into_type";

    public static final String COL_BANK_RETURN_MSG = "bank_return_msg";

    public static final String COL_REMARK = "remark";

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