package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeCustomerInquiry")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_customer_inquiry")
public class OpeCustomerInquiry {
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
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    @TableLogic
    private Integer dr;

    /**
     * 客户id
     */
    @TableField(value = "customer_id")
    @ApiModelProperty(value = "客户id")
    private Long customerId;

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
    @TableField(value = "district")
    @ApiModelProperty(value = "区域")
    private Long district;

    /**
     * 客户来源渠道 官网/email/电话
     */
    @TableField(value = "customer_source")
    @ApiModelProperty(value = "客户来源渠道 官网/email/电话")
    private String customerSource;

    /**
     * 销售员id
     */
    @TableField(value = "sales_id")
    @ApiModelProperty(value = "销售员id")
    private Long salesId;

    /**
     * 状态 已处理/未处理（默认）
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态 已处理/未处理（默认）")
    private String status;

    /**
     * 行业 行业
     */
    @TableField(value = "industry")
    @ApiModelProperty(value = "行业 行业")
    private String industry;

    /**
     * 客户类型 企业/个人
     */
    @TableField(value = "customer_type")
    @ApiModelProperty(value = "客户类型 企业/个人")
    private String customerType;

    /**
     * 客户名称
     */
    @TableField(value = "first_name")
    @ApiModelProperty(value = "客户名称")
    private String firstName;

    /**
     * 姓
     */
    @TableField(value = "last_name")
    @ApiModelProperty(value = "姓")
    private String lastName;

    /**
     * 全名
     */
    @TableField(value = "full_name")
    @ApiModelProperty(value = "全名")
    private String fullName;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 国家编码如+86
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode;

    /**
     * 电话
     */
    @TableField(value = "telephone")
    @ApiModelProperty(value = "电话")
    private String telephone;

    /**
     * 客户邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "客户邮箱")
    private String email;

    /**
     * 客户地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "客户地址")
    private String address;

    /**
     * 联系人
     */
    @TableField(value = "contact_first")
    @ApiModelProperty(value = "联系人")
    private String contactFirst;

    @TableField(value = "contact_last")
    @ApiModelProperty(value = "")
    private String contactLast;

    @TableField(value = "contant_full_name")
    @ApiModelProperty(value = "")
    private String contantFullName;

    /**
     * 需求车辆数
     */
    @TableField(value = "scooter_quantity")
    @ApiModelProperty(value = "需求车辆数")
    private Integer scooterQuantity;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;

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

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

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

    public static final String COL_SCOOTER_QUANTITY = "scooter_quantity";

    public static final String COL_REMARKS = "remarks";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

    public static OpeCustomerInquiryBuilder builder() {
        return new OpeCustomerInquiryBuilder();
    }
}