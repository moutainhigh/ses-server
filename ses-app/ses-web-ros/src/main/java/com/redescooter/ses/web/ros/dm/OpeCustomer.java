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

@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeCustomer")
@Data
@TableName(value = "ope_customer")
public class OpeCustomer implements Serializable {
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
     * 国家
     */
    @TableField(value = "country")
    @ApiModelProperty(value="国家")
    private Long country;

    /**
     * 城市
     */
    @TableField(value = "city")
    @ApiModelProperty(value="城市")
    private Long city;

    /**
     * 区域
     */
    @TableField(value = "distrust")
    @ApiModelProperty(value="区域")
    private Long distrust;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态")
    private String status;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户id")
    private Long tenantId;

    /**
     * 销售
     */
    @TableField(value = "sales_id")
    @ApiModelProperty(value="销售")
    private Long salesId;

    /**
     * 客户名
     */
    @TableField(value = "name")
    @ApiModelProperty(value="客户名")
    private String name;

    /**
     * 客户头像
     */
    @TableField(value = "picture")
    @ApiModelProperty(value="客户头像")
    private String picture;

    /**
     * 客户来源渠道 官网/email/电话
     */
    @TableField(value = "customer_source")
    @ApiModelProperty(value="客户来源渠道 官网/email/电话")
    private String customerSource;

    /**
     * 客户类型 企业/个人
     */
    @TableField(value = "customer_type")
    @ApiModelProperty(value="客户类型 企业/个人")
    private String customerType;

    /**
     * 客户行业
     */
    @TableField(value = "industry")
    @ApiModelProperty(value="客户行业")
    private String industry;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    @ApiModelProperty(value="经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value="纬度")
    private BigDecimal latitude;

    /**
     * 联系人
     */
    @TableField(value = "contact")
    @ApiModelProperty(value="联系人")
    private String contact;

    /**
     * 电话
     */
    @TableField(value = "telephone")
    @ApiModelProperty(value="电话")
    private String telephone;

    /**
     * 邮件
     */
    @TableField(value = "email")
    @ApiModelProperty(value="邮件")
    private String email;

    /**
     * 车辆数量
     */
    @TableField(value = "scooter_quantity")
    @ApiModelProperty(value="车辆数量")
    private Integer scooterQuantity;

    /**
     * 身份证号
     */
    @TableField(value = "id_card")
    @ApiModelProperty(value="身份证号")
    private String idCard;

    /**
     * 身份证正面图片
     */
    @TableField(value = "id_card_positive")
    @ApiModelProperty(value="身份证正面图片")
    private String idCardPositive;

    /**
     * 身份证反面图片
     */
    @TableField(value = "id_card_negative")
    @ApiModelProperty(value="身份证反面图片")
    private String idCardNegative;

    /**
     * 营业执照编号
     */
    @TableField(value = "business_license_num")
    @ApiModelProperty(value="营业执照编号")
    private String businessLicenseNum;

    /**
     * 营业执照图片
     */
    @TableField(value = "business_license_picture")
    @ApiModelProperty(value="营业执照图片")
    private String businessLicensePicture;

    /**
     * 发票附件1
     */
    @TableField(value = "invoice_attachment1")
    @ApiModelProperty(value="发票附件1")
    private String invoiceAttachment1;

    /**
     * 发票附件2
     */
    @TableField(value = "invoice_attachment2")
    @ApiModelProperty(value="发票附件2")
    private String invoiceAttachment2;

    /**
     * 合同 多个图片逗号隔开
     */
    @TableField(value = "contract")
    @ApiModelProperty(value="合同 多个图片逗号隔开")
    private String contract;

    /**
     * 时区
     */
    @TableField(value = "time_zone")
    @ApiModelProperty(value="时区")
    private String timeZone;

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
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

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

    public static final String COL_COUNTRY = "country";

    public static final String COL_CITY = "city";

    public static final String COL_DISTRUST = "distrust";

    public static final String COL_STATUS = "status";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_SALES_ID = "sales_id";

    public static final String COL_NAME = "name";

    public static final String COL_PICTURE = "picture";

    public static final String COL_CUSTOMER_SOURCE = "customer_source";

    public static final String COL_CUSTOMER_TYPE = "customer_type";

    public static final String COL_INDUSTRY = "industry";

    public static final String COL_ADDRESS = "address";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_CONTACT = "contact";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_EMAIL = "email";

    public static final String COL_SCOOTER_QUANTITY = "scooter_quantity";

    public static final String COL_ID_CARD = "id_card";

    public static final String COL_ID_CARD_POSITIVE = "id_card_positive";

    public static final String COL_ID_CARD_NEGATIVE = "id_card_negative";

    public static final String COL_BUSINESS_LICENSE_NUM = "business_license_num";

    public static final String COL_BUSINESS_LICENSE_PICTURE = "business_license_picture";

    public static final String COL_INVOICE_ATTACHMENT1 = "invoice_attachment1";

    public static final String COL_INVOICE_ATTACHMENT2 = "invoice_attachment2";

    public static final String COL_CONTRACT = "contract";

    public static final String COL_TIME_ZONE = "time_zone";

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