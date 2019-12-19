package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "com.redescooter.ses.web.ros.dm.OpeCustomer")
@Data
@TableName(value = "ope_customer")
public class OpeCustomer implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * 时区
     */
    @TableField(value = "time_zone")
    @ApiModelProperty(value = "时区")
    private String timeZone;

    /**
     * 国家
     */
    @TableField(value = "country")
    @ApiModelProperty(value = "国家")
    private Long country;

    /**
     * 城市
     */
    @TableField(value = "city")
    @ApiModelProperty(value = "城市")
    private Long city;

    /**
     * 区域
     */
    @TableField(value = "distrust")
    @ApiModelProperty(value = "区域")
    private Long distrust;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 销售
     */
    @TableField(value = "sales_id")
    @ApiModelProperty(value = "销售")
    private Long salesId;

    /**
     * 客户名字
     */
    @TableField(value = "customer_first_name")
    @ApiModelProperty(value = "客户名字")
    private String customerFirstName;

    /**
     * 客户姓氏
     */
    @TableField(value = "customer_last_name")
    @ApiModelProperty(value = "客户姓氏")
    private String customerLastName;

    /**
     * 客户全名
     */
    @TableField(value = "customer_full_name")
    @ApiModelProperty(value = "客户全名")
    private String customerFullName;

    /**
     * 企业名称
     */
    @TableField(value = "company_name")
    @ApiModelProperty(value = "企业名称")
    private String companyName;

    /**
     * 客户头像
     */
    @TableField(value = "picture")
    @ApiModelProperty(value = "客户头像")
    private String picture;

    /**
     * 客户来源渠道 官网/email/电话
     */
    @TableField(value = "customer_source")
    @ApiModelProperty(value = "客户来源渠道 官网/email/电话")
    private String customerSource;

    /**
     * 客户类型 1企业/2个人
     */
    @TableField(value = "customer_type")
    @ApiModelProperty(value = "客户类型 1企业/2个人")
    private String customerType;

    /**
     * 客户行业类型，1餐厅/2快递
     */
    @TableField(value = "industry_type")
    @ApiModelProperty(value = "客户行业类型，1餐厅/2快递")
    private String industryType;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 地点编号
     */
    @TableField(value = "place_id")
    @ApiModelProperty(value = "地点编号")
    private String placeId;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    /**
     * 联系人名字
     */
    @TableField(value = "contact_first_name")
    @ApiModelProperty(value = "联系人名字")
    private String contactFirstName;

    /**
     * 联系人姓氏
     */
    @TableField(value = "contact_last_name")
    @ApiModelProperty(value = "联系人姓氏")
    private String contactLastName;

    /**
     * 联系人全名
     */
    @TableField(value = "contact_full_name")
    @ApiModelProperty(value = "联系人全名")
    private String contactFullName;

    /**
     * 电话
     */
    @TableField(value = "telephone")
    @ApiModelProperty(value = "电话")
    private String telephone;

    /**
     * 邮件
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮件")
    private String email;

    /**
     * 备注信息
     */
    @TableField(value = "memo")
    @ApiModelProperty(value = "备注信息")
    private String memo;

    /**
     * 车辆数量
     */
    @TableField(value = "scooter_quantity")
    @ApiModelProperty(value = "车辆数量")
    private Integer scooterQuantity;

    /**
     * 证件类型1身份证，2驾驶证，3护照
     */
    @TableField(value = "certificate_type")
    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

    /**
     * 证件正面图片
     */
    @TableField(value = "certificate_positive_annex")
    @ApiModelProperty(value = "证件正面图片")
    private String certificatePositiveAnnex;

    /**
     * 证件反面图片
     */
    @TableField(value = "certificate_negative_annex")
    @ApiModelProperty(value = "证件反面图片")
    private String certificateNegativeAnnex;

    /**
     * 营业执照编号
     */
    @TableField(value = "business_license_num")
    @ApiModelProperty(value = "营业执照编号")
    private String businessLicenseNum;

    /**
     * 营业执照图片
     */
    @TableField(value = "business_license_annex")
    @ApiModelProperty(value = "营业执照图片")
    private String businessLicenseAnnex;

    /**
     * 发票编号
     */
    @TableField(value = "invoice_num")
    @ApiModelProperty(value = "发票编号")
    private String invoiceNum;

    /**
     * 发票附件
     */
    @TableField(value = "invoice_annex")
    @ApiModelProperty(value = "发票附件")
    private String invoiceAnnex;

    /**
     * 合同附件
     */
    @TableField(value = "contract_annex")
    @ApiModelProperty(value = "合同附件")
    private String contractAnnex;

    /**
     * 账号使用标识，即激活使用过1，未激活未使用2
     */
    @TableField(value = "account_flag")
    @ApiModelProperty(value = "账号使用标识，即激活使用过1，未激活未使用2")
    private Integer accountFlag;

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

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_TIME_ZONE = "time_zone";

    public static final String COL_COUNTRY = "country";

    public static final String COL_CITY = "city";

    public static final String COL_DISTRUST = "distrust";

    public static final String COL_STATUS = "status";

    public static final String COL_SALES_ID = "sales_id";

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