package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSupplier")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_supplier")
public class OpeSupplier {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识")
    @TableLogic
    private Integer dr;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 采购商名称
     */
    @TableField(value = "supplier_name")
    @ApiModelProperty(value = "采购商名称")
    private String supplierName;

    /**
     * 采购商地址
     */
    @TableField(value = "supplier_address")
    @ApiModelProperty(value = "采购商地址")
    private String supplierAddress;

    /**
     * 采购商国家
     */
    @TableField(value = "supplier_country")
    @ApiModelProperty(value = "采购商国家")
    private String supplierCountry;

    /**
     * 采购商经度
     */
    @TableField(value = "supplier_longitude")
    @ApiModelProperty(value = "采购商经度")
    private BigDecimal supplierLongitude;

    /**
     * 采购商纬度
     */
    @TableField(value = "supplier_latitude")
    @ApiModelProperty(value = "采购商纬度")
    private BigDecimal supplierLatitude;

    /**
     * 地址唯一ID
     */
    @TableField(value = "place_id")
    @ApiModelProperty(value = "地址唯一ID")
    private String placeId;

    /**
     * geo_hash
     */
    @TableField(value = "geo_hash")
    @ApiModelProperty(value = "geo_hash")
    private String geoHash;

    /**
     * 采购商标签
     */
    @TableField(value = "supplier_tag")
    @ApiModelProperty(value = "采购商标签")
    private String supplierTag;

    /**
     * 采购商备注
     */
    @TableField(value = "supplier_memo")
    @ApiModelProperty(value = "采购商备注")
    private String supplierMemo;

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
     * 联系人邮箱
     */
    @TableField(value = "contact_email")
    @ApiModelProperty(value = "联系人邮箱")
    private String contactEmail;

    /**
     * 手机号归属国家
     */
    @TableField(value = "contact_phone_country_code")
    @ApiModelProperty(value = "手机号归属国家")
    private String contactPhoneCountryCode;

    /**
     * 联系人手机号
     */
    @TableField(value = "contact_phone")
    @ApiModelProperty(value = "联系人手机号")
    private String contactPhone;

    /**
     * 付款周期
     */
    @TableField(value = "payment_cycle")
    @ApiModelProperty(value = "付款周期")
    private Integer paymentCycle;

    /**
     * 合作开始时间
     */
    @TableField(value = "cooperation_time_start")
    @ApiModelProperty(value = "合作开始时间")
    private Date cooperationTimeStart;

    /**
     * 合作结束时间
     */
    @TableField(value = "cooperation_time_end")
    @ApiModelProperty(value = "合作结束时间")
    private Date cooperationTimeEnd;

    /**
     * 营业执照编号
     */
    @TableField(value = "business_number")
    @ApiModelProperty(value = "营业执照编号")
    private String businessNumber;

    /**
     * 营业执照附件
     */
    @TableField(value = "business_license_annex")
    @ApiModelProperty(value = "营业执照附件")
    private String businessLicenseAnnex;

    /**
     * 合同编号
     */
    @TableField(value = "contract_number")
    @ApiModelProperty(value = "合同编号")
    private String contractNumber;

    /**
     * 合同附件
     */
    @TableField(value = "contract_annex")
    @ApiModelProperty(value = "合同附件")
    private String contractAnnex;

    /**
     * 是否过期：默认0不过期，-1过期
     */
    @TableField(value = "overdue_flag")
    @ApiModelProperty(value = "是否过期：默认0不过期，-1过期")
    private Integer overdueFlag;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

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
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_SUPPLIER_NAME = "supplier_name";

    public static final String COL_SUPPLIER_ADDRESS = "supplier_address";

    public static final String COL_SUPPLIER_COUNTRY = "supplier_country";

    public static final String COL_SUPPLIER_LONGITUDE = "supplier_longitude";

    public static final String COL_SUPPLIER_LATITUDE = "supplier_latitude";

    public static final String COL_PLACE_ID = "place_id";

    public static final String COL_GEO_HASH = "geo_hash";

    public static final String COL_SUPPLIER_TAG = "supplier_tag";

    public static final String COL_SUPPLIER_MEMO = "supplier_memo";

    public static final String COL_CONTACT_FIRST_NAME = "contact_first_name";

    public static final String COL_CONTACT_LAST_NAME = "contact_last_name";

    public static final String COL_CONTACT_FULL_NAME = "contact_full_name";

    public static final String COL_CONTACT_EMAIL = "contact_email";

    public static final String COL_CONTACT_PHONE_COUNTRY_CODE = "contact_phone_country_code";

    public static final String COL_CONTACT_PHONE = "contact_phone";

    public static final String COL_PAYMENT_CYCLE = "payment_cycle";

    public static final String COL_COOPERATION_TIME_START = "cooperation_time_start";

    public static final String COL_COOPERATION_TIME_END = "cooperation_time_end";

    public static final String COL_BUSINESS_NUMBER = "business_number";

    public static final String COL_BUSINESS_LICENSE_ANNEX = "business_license_annex";

    public static final String COL_CONTRACT_NUMBER = "contract_number";

    public static final String COL_CONTRACT_ANNEX = "contract_annex";

    public static final String COL_OVERDUE_FLAG = "overdue_flag";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF6 = "def6";

}