package com.redescooter.ses.service.hub.source.operation.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_customer")
public class OpeCustomer implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    private Integer dr;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    private Long tenantId;

    /**
     * 时区
     */
    @TableField(value = "time_zone")
    private String timeZone;

    /**
     * 国家
     */
    @TableField(value = "country")
    private Long country;

    /**
     * 国家编码，如手机号 中国 +86
     */
    @TableField(value = "country_code")
    private String countryCode;

    /**
     * 城市
     */
    @TableField(value = "city")
    private Long city;

    /**
     * 区域
     */
    @TableField(value = "distrust")
    private Long distrust;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 销售
     */
    @TableField(value = "sales_id")
    private Long salesId;

    /**
     * 客户编码
     */
    @TableField(value = "customer_code")
    private String customerCode;

    /**
     * 客户名字
     */
    @TableField(value = "customer_first_name")
    private String customerFirstName;

    /**
     * 客户姓氏
     */
    @TableField(value = "customer_last_name")
    private String customerLastName;

    /**
     * 客户全名
     */
    @TableField(value = "customer_full_name")
    private String customerFullName;

    /**
     * 企业名称
     */
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 客户头像
     */
    @TableField(value = "picture")
    private String picture;

    /**
     * 客户来源渠道 官网/email/电话
     */
    @TableField(value = "customer_source")
    private String customerSource;

    /**
     * 客户类型 1企业/2个人
     */
    @TableField(value = "customer_type")
    private String customerType;

    /**
     * 客户行业类型，1餐厅/2快递
     */
    @TableField(value = "industry_type")
    private String industryType;

    /**
     * 地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 地点编号
     */
    @TableField(value = "place_id")
    private String placeId;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    private BigDecimal latitude;

    /**
     * 联系人名字
     */
    @TableField(value = "contact_first_name")
    private String contactFirstName;

    /**
     * 联系人姓氏
     */
    @TableField(value = "contact_last_name")
    private String contactLastName;

    /**
     * 联系人全名
     */
    @TableField(value = "contact_full_name")
    private String contactFullName;

    /**
     * 电话
     */
    @TableField(value = "telephone")
    private String telephone;

    /**
     * 邮件
     */
    @TableField(value = "email")
    private String email;

    /**
     * 备注信息
     */
    @TableField(value = "memo")
    private String memo;

    /**
     * 车辆数量
     */
    @TableField(value = "scooter_quantity")
    private Integer scooterQuantity;

    /**
     * 已分配车辆的数量
     */
    @TableField(value = "assignation_scooter_qty")
    private Integer assignationScooterQty;

    /**
     * 证件类型1身份证，2驾驶证，3护照
     */
    @TableField(value = "certificate_type")
    private String certificateType;

    /**
     * 证件正面图片
     */
    @TableField(value = "certificate_positive_annex")
    private String certificatePositiveAnnex;

    /**
     * 证件反面图片
     */
    @TableField(value = "certificate_negative_annex")
    private String certificateNegativeAnnex;

    /**
     * 营业执照编号
     */
    @TableField(value = "business_license_num")
    private String businessLicenseNum;

    /**
     * 营业执照图片
     */
    @TableField(value = "business_license_annex")
    private String businessLicenseAnnex;

    /**
     * 发票编号
     */
    @TableField(value = "invoice_num")
    private String invoiceNum;

    /**
     * 发票附件
     */
    @TableField(value = "invoice_annex")
    private String invoiceAnnex;

    /**
     * 合同附件
     */
    @TableField(value = "contract_annex")
    private String contractAnnex;

    /**
     * 账号使用标识，即激活使用过1，未激活未使用0
     */
    @TableField(value = "account_flag")
    private String accountFlag;

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
     * 更新人
     */
    @TableField(value = "updated_by")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    @TableField(value = "def1")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_TIME_ZONE = "time_zone";

    public static final String COL_COUNTRY = "country";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_CITY = "city";

    public static final String COL_DISTRUST = "distrust";

    public static final String COL_STATUS = "status";

    public static final String COL_SALES_ID = "sales_id";

    public static final String COL_CUSTOMER_CODE = "customer_code";

    public static final String COL_CUSTOMER_FIRST_NAME = "customer_first_name";

    public static final String COL_CUSTOMER_LAST_NAME = "customer_last_name";

    public static final String COL_CUSTOMER_FULL_NAME = "customer_full_name";

    public static final String COL_COMPANY_NAME = "company_name";

    public static final String COL_PICTURE = "picture";

    public static final String COL_CUSTOMER_SOURCE = "customer_source";

    public static final String COL_CUSTOMER_TYPE = "customer_type";

    public static final String COL_INDUSTRY_TYPE = "industry_type";

    public static final String COL_ADDRESS = "address";

    public static final String COL_PLACE_ID = "place_id";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_CONTACT_FIRST_NAME = "contact_first_name";

    public static final String COL_CONTACT_LAST_NAME = "contact_last_name";

    public static final String COL_CONTACT_FULL_NAME = "contact_full_name";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_EMAIL = "email";

    public static final String COL_MEMO = "memo";

    public static final String COL_SCOOTER_QUANTITY = "scooter_quantity";

    public static final String COL_ASSIGNATION_SCOOTER_QTY = "assignation_scooter_qty";

    public static final String COL_CERTIFICATE_TYPE = "certificate_type";

    public static final String COL_CERTIFICATE_POSITIVE_ANNEX = "certificate_positive_annex";

    public static final String COL_CERTIFICATE_NEGATIVE_ANNEX = "certificate_negative_annex";

    public static final String COL_BUSINESS_LICENSE_NUM = "business_license_num";

    public static final String COL_BUSINESS_LICENSE_ANNEX = "business_license_annex";

    public static final String COL_INVOICE_NUM = "invoice_num";

    public static final String COL_INVOICE_ANNEX = "invoice_annex";

    public static final String COL_CONTRACT_ANNEX = "contract_annex";

    public static final String COL_ACCOUNT_FLAG = "account_flag";

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