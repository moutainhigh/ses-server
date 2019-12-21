package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com.redescooter.ses.service.foundation.dm.base.PlaTenant")
@Data
@TableName(value = "pla_tenant")
public class PlaTenant implements Serializable {
    /**
     * ID
     */
     @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * P_ID
     */
    @TableField(value = "p_id")
    @ApiModelProperty(value="P_ID")
    private Long pId;

    /**
     * 租户名，即客户名
     */
    @TableField(value = "tenant_name")
    @ApiModelProperty(value="租户名，即客户名")
    private String tenantName;

    /**
     * 邮件
     */
    @TableField(value = "email")
    @ApiModelProperty(value="邮件")
    private String email;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态")
    private String status;

    @TableField(value = "country_id")
    @ApiModelProperty(value="null")
    private Long countryId;

    /**
     * city_Id
     */
    @TableField(value = "city_id")
    @ApiModelProperty(value="city_Id")
    private Long cityId;

    @TableField(value = "distrust_id")
    @ApiModelProperty(value="null")
    private Long distrustId;

    /**
     * 司机数量
     */
    @TableField(value = "driver_counts")
    @ApiModelProperty(value="司机数量")
    private Integer driverCounts;

    /**
     * 销售
     */
    @TableField(value = "sales_id")
    @ApiModelProperty(value="销售")
    private Long salesId;

    /**
     * 来源渠道 官网/Email/电话
     */
    @TableField(value = "tenant_source")
    @ApiModelProperty(value="来源渠道 官网/Email/电话")
    private String tenantSource;

    /**
     * 租户类型 企业/个人
     */
    @TableField(value = "tenant_type")
    @ApiModelProperty(value="租户类型 企业/个人")
    private String tenantType;

    /**
     * 租户行业
     */
    @TableField(value = "tenant_industry")
    @ApiModelProperty(value="租户行业")
    private String tenantIndustry;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 联系人
     */
    @TableField(value = "contact")
    @ApiModelProperty(value="联系人")
    private String contact;

    /**
     * 职位
     */
    @TableField(value = "position")
    @ApiModelProperty(value="职位")
    private String position;

    /**
     * 租户编码
     */
    @TableField(value = "tenant_code")
    @ApiModelProperty(value="租户编码")
    private String tenantCode;

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
     * 电话
     */
    @TableField(value = "tel_1")
    @ApiModelProperty(value="电话")
    private String tel1;

    /**
     * 电话
     */
    @TableField(value = "tel_2")
    @ApiModelProperty(value="电话")
    private String tel2;

    /**
     * 电话
     */
    @TableField(value = "tel_3")
    @ApiModelProperty(value="电话")
    private String tel3;

    /**
     * 邮件
     */
    @TableField(value = "email_1")
    @ApiModelProperty(value="邮件")
    private String email1;

    /**
     * 邮件
     */
    @TableField(value = "email_2")
    @ApiModelProperty(value="邮件")
    private String email2;

    /**
     * 邮件
     */
    @TableField(value = "email_3")
    @ApiModelProperty(value="邮件")
    private String email3;

    /**
     * 时区
     */
    @TableField(value = "time_zone")
    @ApiModelProperty(value="时区")
    private String timeZone;

    /**
     * 生效时间
     */
    @TableField(value = "effective_time")
    @ApiModelProperty(value="生效时间")
    private Date effectiveTime;

    /**
     * 到期时间
     */
    @TableField(value = "expire_time")
    @ApiModelProperty(value="到期时间")
    private Date expireTime;

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

    public static final String COL_DR = "dr";

    public static final String COL_P_ID = "p_id";

    public static final String COL_TENANT_NAME = "tenant_name";

    public static final String COL_EMAIL = "email";

    public static final String COL_STATUS = "status";

    public static final String COL_COUNTRY_ID = "country_id";

    public static final String COL_CITY_ID = "city_id";

    public static final String COL_DISTRUST_ID = "distrust_id";

    public static final String COL_DRIVER_COUNTS = "driver_counts";

    public static final String COL_SALES_ID = "sales_id";

    public static final String COL_TENANT_SOURCE = "tenant_source";

    public static final String COL_TENANT_TYPE = "tenant_type";

    public static final String COL_TENANT_INDUSTRY = "tenant_industry";

    public static final String COL_ADDRESS = "address";

    public static final String COL_CONTACT = "contact";

    public static final String COL_POSITION = "position";

    public static final String COL_TENANT_CODE = "tenant_code";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_TEL_1 = "tel_1";

    public static final String COL_TEL_2 = "tel_2";

    public static final String COL_TEL_3 = "tel_3";

    public static final String COL_EMAIL_1 = "email_1";

    public static final String COL_EMAIL_2 = "email_2";

    public static final String COL_EMAIL_3 = "email_3";

    public static final String COL_TIME_ZONE = "time_zone";

    public static final String COL_EFFECTIVE_TIME = "effective_time";

    public static final String COL_EXPIRE_TIME = "expire_time";

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