package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户咨询管理
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeCustomerInquiry")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_customer_inquiry")
public class OpeCustomerInquiry implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value="订单号")
    private String orderNo;

    /**
     * 客户id
     */
    @TableField(value = "customer_id")
    @ApiModelProperty(value="客户id")
    private Long customerId;

    /**
     * 国家
     */
    @TableField(value = "country")
    @ApiModelProperty(value="国家")
    private String country;

    /**
     * 城市
     */
    @TableField(value = "city")
    @ApiModelProperty(value="城市")
    private String city;

    /**
     * 区域
     */
    @TableField(value = "district")
    @ApiModelProperty(value="区域")
    private String district;

    /**
     * 邮编
     */
    @TableField(value = "post_code")
    @ApiModelProperty(value="邮编")
    private String postCode;

    /**
     * 客户来源渠道 官网/email/电话
     */
    @TableField(value = "customer_source")
    @ApiModelProperty(value="客户来源渠道 官网/email/电话")
    private String customerSource;

    /**
     * 销售员id
     */
    @TableField(value = "sales_id")
    @ApiModelProperty(value="销售员id")
    private Long salesId;

    /**
     * 状态 已处理/未处理（默认）
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 已处理/未处理（默认）")
    private String status;

    /**
     * 行业 行业
     */
    @TableField(value = "industry")
    @ApiModelProperty(value="行业 行业")
    private String industry;

    /**
     * 客户类型 企业/个人
     */
    @TableField(value = "customer_type")
    @ApiModelProperty(value="客户类型 企业/个人")
    private String customerType;

    /**
     * 客户名字
     */
    @TableField(value = "first_name")
    @ApiModelProperty(value="客户名字")
    private String firstName;

    /**
     * 客户名字
     */
    @TableField(value = "last_name")
    @ApiModelProperty(value="客户名字")
    private String lastName;

    /**
     * 客户名字
     */
    @TableField(value = "full_name")
    @ApiModelProperty(value="客户名字")
    private String fullName;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    @ApiModelProperty(value="公司名称")
    private String companyName;

    /**
     * 国家编码如+86
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value="国家编码如+86")
    private String countryCode;

    /**
     * 电话
     */
    @TableField(value = "telephone")
    @ApiModelProperty(value="电话")
    private String telephone;

    /**
     * 客户邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value="客户邮箱")
    private String email;

    /**
     * 客户地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="客户地址")
    private String address;

    /**
     * 联系人
     */
    @TableField(value = "contact_first")
    @ApiModelProperty(value="联系人")
    private String contactFirst;

    @TableField(value = "contact_last")
    @ApiModelProperty(value="")
    private String contactLast;

    @TableField(value = "contant_full_name")
    @ApiModelProperty(value="")
    private String contantFullName;

    /**
     * 产品Id
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value="产品Id ")
    private Long productId;

    /**
     * 产品型号
     */
    @TableField(value = "product_model")
    @ApiModelProperty(value="产品型号")
    private String productModel;

    /**
     * 产品单价
     */
    @TableField(value = "product_price")
    @ApiModelProperty(value="产品单价")
    private BigDecimal productPrice;

    /**
     * 单据总价
     */
    @TableField(value = "total_price")
    @ApiModelProperty(value="单据总价")
    private BigDecimal totalPrice;

    /**
     * 已付金额
     */
    @TableField(value = "amount_paid")
    @ApiModelProperty(value="已付金额")
    private BigDecimal amountPaid;

    /**
     * 待付款金额
     */
    @TableField(value = "amount_obligation")
    @ApiModelProperty(value="待付款金额")
    private BigDecimal amountObligation;

    /**
     * 预付定金
     */
    @TableField(value = "prepaid_deposit")
    @ApiModelProperty(value="预付定金")
    private BigDecimal prepaidDeposit;

    /**
     * 优惠抵扣金额
     */
    @TableField(value = "amount_discount")
    @ApiModelProperty(value="优惠抵扣金额")
    private BigDecimal amountDiscount;

    /**
     * 支付状态
     */
    @TableField(value = "pay_status")
    @ApiModelProperty(value="支付状态")
    private String payStatus;

    /**
     * 需求车辆数
     */
    @TableField(value = "scooter_quantity")
    @ApiModelProperty(value="需求车辆数")
    private Integer scooterQuantity;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    @ApiModelProperty(value="备注")
    private String remarks;

    /**
     * 来源1 询价单 2预订单
     */
    @TableField(value = "`source`")
    @ApiModelProperty(value="来源1 询价单 2预订单")
    private String source;

    /**
     * 银行卡上面名字
     */
    @TableField(value = "bank_card_name")
    @ApiModelProperty(value="银行卡上面名字")
    private String bankCardName;

    /**
     * cvv
     */
    @TableField(value = "cvv")
    @ApiModelProperty(value="cvv")
    private String cvv;

    /**
     * 过期时间
     */
    @TableField(value = "expired_time")
    @ApiModelProperty(value="过期时间")
    private Date expiredTime;

    /**
     * 卡号
     */
    @TableField(value = "card_Num")
    @ApiModelProperty(value="卡号")
    private String cardNum;

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
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

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
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_CUSTOMER_ID = "customer_id";

    public static final String COL_COUNTRY = "country";

    public static final String COL_CITY = "city";

    public static final String COL_DISTRICT = "district";

    public static final String COL_POST_CODE = "post_code";

    public static final String COL_CUSTOMER_SOURCE = "customer_source";

    public static final String COL_SALES_ID = "sales_id";

    public static final String COL_STATUS = "status";

    public static final String COL_INDUSTRY = "industry";

    public static final String COL_CUSTOMER_TYPE = "customer_type";

    public static final String COL_FIRST_NAME = "first_name";

    public static final String COL_LAST_NAME = "last_name";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_COMPANY_NAME = "company_name";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_EMAIL = "email";

    public static final String COL_ADDRESS = "address";

    public static final String COL_CONTACT_FIRST = "contact_first";

    public static final String COL_CONTACT_LAST = "contact_last";

    public static final String COL_CONTANT_FULL_NAME = "contant_full_name";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_PRODUCT_MODEL = "product_model";

    public static final String COL_PRODUCT_PRICE = "product_price";

    public static final String COL_TOTAL_PRICE = "total_price";

    public static final String COL_AMOUNT_PAID = "amount_paid";

    public static final String COL_AMOUNT_OBLIGATION = "amount_obligation";

    public static final String COL_PREPAID_DEPOSIT = "prepaid_deposit";

    public static final String COL_AMOUNT_DISCOUNT = "amount_discount";

    public static final String COL_PAY_STATUS = "pay_status";

    public static final String COL_SCOOTER_QUANTITY = "scooter_quantity";

    public static final String COL_REMARKS = "remarks";

    public static final String COL_SOURCE = "source";

    public static final String COL_BANK_CARD_NAME = "bank_card_name";

    public static final String COL_CVV = "cvv";

    public static final String COL_EXPIRED_TIME = "expired_time";

    public static final String COL_CARD_NUM = "card_Num";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}
