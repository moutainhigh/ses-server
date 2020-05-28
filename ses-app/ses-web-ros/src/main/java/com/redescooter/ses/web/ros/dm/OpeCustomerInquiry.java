package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;

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
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    private Integer dr;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    private String orderNo;

    /**
     * 客户id
     */
    @TableField(value = "customer_id")
    private Long customerId;

    /**
     * 国家
     */
    @TableField(value = "country")
    private Long country;

    /**
     * 城市
     */
    @TableField(value = "city")
    private Long city;

    /**
     * 区域
     */
    @TableField(value = "district")
    private Long district;

    /**
     * 客户来源渠道 官网/email/电话
     */
    @TableField(value = "customer_source")
    private String customerSource;

    /**
     * 销售员id
     */
    @TableField(value = "sales_id")
    private Long salesId;

    /**
     * 状态 已处理/未处理（默认）
     */
    @TableField(value = "status")
    private String status;

    /**
     * 行业 行业
     */
    @TableField(value = "industry")
    private String industry;

    /**
     * 客户类型 企业/个人
     */
    @TableField(value = "customer_type")
    private String customerType;

    /**
     * 客户名称
     */
    @TableField(value = "first_name")
    private String firstName;

    /**
     * 姓
     */
    @TableField(value = "last_name")
    private String lastName;

    /**
     * 全名
     */
    @TableField(value = "full_name")
    private String fullName;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 国家编码如+86
     */
    @TableField(value = "country_code")
    private String countryCode;

    /**
     * 电话
     */
    @TableField(value = "telephone")
    private String telephone;

    /**
     * 客户邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 客户地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 联系人
     */
    @TableField(value = "contact_first")
    private String contactFirst;

    @TableField(value = "contact_last")
    private String contactLast;

    @TableField(value = "contant_full_name")
    private String contantFullName;

    /**
     * 产品Id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 产品型号
     */
    @TableField(value = "product_model")
    private String productModel;

    /**
     * 产品单价
     */
    @TableField(value = "product_price")
    private BigDecimal productPrice;

    /**
     * 单据总价
     */
    @TableField(value = "total_price")
    private BigDecimal totalPrice;

    /**
     * 需求车辆数
     */
    @TableField(value = "scooter_quantity")
    private Integer scooterQuantity;

    /**
     * 支付状态
     */
    @TableField(value = "pay_status")
    private String payStatus;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    /**
     * 来源1 询价单 2预订单
     */
    @TableField(value = "source")
    private String source;

    /**
     * 银行卡上面名字
     */
    @TableField(value = "bank_card_name")
    private String bankCardName;

    /**
     * cvv
     */
    @TableField(value = "cvv")
    private String cvv;

    /**
     * 过期时间
     */
    @TableField(value = "expired_time")
    private Date expiredTime;

    /**
     * 卡号
     */
    @TableField(value = "card_Num")
    private String cardNum;

    /**
     * 安全码
     */
    @TableField(value = "postal_code")
    private String postalCode;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    private Long updatedBy;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_CUSTOMER_ID = "customer_id";

    public static final String COL_COUNTRY = "country";

    public static final String COL_CITY = "city";

    public static final String COL_DISTRICT = "district";

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

    public static final String COL_SCOOTER_QUANTITY = "scooter_quantity";

    public static final String COL_PAY_STATUS = "pay_status";

    public static final String COL_REMARKS = "remarks";

    public static final String COL_SOURCE = "source";

    public static final String COL_BANK_CARD_NAME = "bank_card_name";

    public static final String COL_CVV = "cvv";

    public static final String COL_EXPIRED_TIME = "expired_time";

    public static final String COL_CARD_NUM = "card_Num";

    public static final String COL_POSTAL_CODE = "postal_code";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_UPDATED_BY = "updated_by";
}
