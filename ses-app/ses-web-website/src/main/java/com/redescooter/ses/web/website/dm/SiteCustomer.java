package com.redescooter.ses.web.website.dm;

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

/**
 * 客户表
 */
@ApiModel(value = "com-redescooter-ses-web-website-dm-SiteCustomer")
@Data
@TableName(value = "site_customer")
public class SiteCustomer implements Serializable {
    /**
     * 主建
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主建")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 状态,-1意向客户，1积极客户，0正式客户
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态,-1意向客户，1积极客户，0正式客户")
    private Integer status;

    /**
     * 国家编号，中国 +86
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value = "国家编号，中国 +86")
    private String countryCode;

    /**
     * 客户来源渠道 ,1官网,2内部,3线下
     */
    @TableField(value = "customer_source")
    @ApiModelProperty(value = "客户来源渠道 ,1官网,2内部,3线下")
    private Integer customerSource;

    /**
     * 销售员ID
     */
    @TableField(value = "sales_id")
    @ApiModelProperty(value = "销售员ID")
    private Long salesId;

    /**
     * 客户行业类型，1餐厅，2快递
     */
    @TableField(value = "industry_type")
    @ApiModelProperty(value = "客户行业类型，1餐厅，2快递")
    private Integer industryType;

    /**
     * 客户类型，1企业，2个人
     */
    @TableField(value = "customer_type")
    @ApiModelProperty(value = "客户类型，1企业，2个人")
    private Integer customerType;

    /**
     * 客户编码
     */
    @TableField(value = "customer_code")
    @ApiModelProperty(value = "客户编码")
    private String customerCode;

    /**
     * 客户头像
     */
    @TableField(value = "customer_head_picture")
    @ApiModelProperty(value = "客户头像")
    private String customerHeadPicture;

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
     * 客户logo
     */
    @TableField(value = "company_logo")
    @ApiModelProperty(value = "客户logo")
    private String companyLogo;

    /**
     * 国家名称
     */
    @TableField(value = "country_name")
    @ApiModelProperty(value = "国家名称")
    private String countryName;

    /**
     * 城市名称
     */
    @TableField(value = "city_name")
    @ApiModelProperty(value = "城市名称")
    private String cityName;

    /**
     * 区域
     */
    @TableField(value = "area_name")
    @ApiModelProperty(value = "区域")
    private String areaName;

    /**
     * 区域邮编
     */
    @TableField(value = "postcode")
    @ApiModelProperty(value = "区域邮编")
    private String postcode;

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
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 已购买车辆数量
     */
    @TableField(value = "purchased_scooter_qty")
    @ApiModelProperty(value = "已购买车辆数量")
    private Integer purchasedScooterQty;

    /**
     * 已分配车辆数
     */
    @TableField(value = "assignation_scooter_qty")
    @ApiModelProperty(value = "已分配车辆数")
    private Integer assignationScooterQty;

    /**
     * 证件类型1身份证，2驾驶证，3护照
     */
    @TableField(value = "certificate_type")
    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
    private Integer certificateType;

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
     * 账号使用标识，即激活使用过1，未激活未使用0
     */
    @TableField(value = "account_flag")
    @ApiModelProperty(value = "账号使用标识，即激活使用过1，未激活未使用0")
    private Integer accountFlag;

    /**
     * 持卡人
     */
    @TableField(value = "cardholder")
    @ApiModelProperty(value = "持卡人")
    private String cardholder;

    /**
     * cvv
     */
    @TableField(value = "cvv")
    @ApiModelProperty(value = "cvv")
    private String cvv;

    /**
     * 卡号
     */
    @TableField(value = "card_num")
    @ApiModelProperty(value = "卡号")
    private String cardNum;

    /**
     * 是否同步
     */
    @TableField(value = "synchronize_flag")
    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

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

    public static final String COL_STATUS = "status";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_CUSTOMER_SOURCE = "customer_source";

    public static final String COL_SALES_ID = "sales_id";

    public static final String COL_INDUSTRY_TYPE = "industry_type";

    public static final String COL_CUSTOMER_TYPE = "customer_type";

    public static final String COL_CUSTOMER_CODE = "customer_code";

    public static final String COL_CUSTOMER_HEAD_PICTURE = "customer_head_picture";

    public static final String COL_CUSTOMER_FIRST_NAME = "customer_first_name";

    public static final String COL_CUSTOMER_LAST_NAME = "customer_last_name";

    public static final String COL_CUSTOMER_FULL_NAME = "customer_full_name";

    public static final String COL_COMPANY_NAME = "company_name";

    public static final String COL_COMPANY_LOGO = "company_logo";

    public static final String COL_COUNTRY_NAME = "country_name";

    public static final String COL_CITY_NAME = "city_name";

    public static final String COL_AREA_NAME = "area_name";

    public static final String COL_POSTCODE = "postcode";

    public static final String COL_ADDRESS = "address";

    public static final String COL_PLACE_ID = "place_id";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_CONTACT_FIRST_NAME = "contact_first_name";

    public static final String COL_CONTACT_LAST_NAME = "contact_last_name";

    public static final String COL_CONTACT_FULL_NAME = "contact_full_name";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_EMAIL = "email";

    public static final String COL_REMARK = "remark";

    public static final String COL_PURCHASED_SCOOTER_QTY = "purchased_scooter_qty";

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

    public static final String COL_CARDHOLDER = "cardholder";

    public static final String COL_CVV = "cvv";

    public static final String COL_CARD_NUM = "card_num";

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