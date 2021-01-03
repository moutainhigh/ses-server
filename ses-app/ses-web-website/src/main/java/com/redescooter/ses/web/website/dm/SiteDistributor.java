package com.redescooter.ses.web.website.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 经销商表
    */
@ApiModel(value="com-redescooter-ses-web-website-dm-SiteDistributor")
@Data
@TableName(value = "site_distributor")
public class SiteDistributor implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 是否删除 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="是否删除 0正常 1删除")
    private Integer dr;

    /**
     * 状态 1启用中 2未启用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 1启用中 2未启用")
    private Integer status;

    /**
     * 门店编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="门店编码")
    private String code;

    /**
     * 门店名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="门店名称")
    private String name;

    /**
     * 门店logo
     */
    @TableField(value = "logo_url")
    @ApiModelProperty(value="门店logo")
    private String logoUrl;

    /**
     * 国家代码 86中国 33法国
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value="国家代码 86中国 33法国")
    private String countryCode;

    /**
     * 电话
     */
    @TableField(value = "tel")
    @ApiModelProperty(value="电话")
    private String tel;

    /**
     * 邮件地址
     */
    @TableField(value = "email")
    @ApiModelProperty(value="邮件地址")
    private String email;

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
    private String longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value="纬度")
    private String latitude;

    /**
     * 邮编
     */
    @TableField(value = "cp")
    @ApiModelProperty(value="邮编")
    private String cp;

    /**
     * 城市
     */
    @TableField(value = "city")
    @ApiModelProperty(value="城市")
    private String city;

    /**
     * 地区
     */
    @TableField(value = "area")
    @ApiModelProperty(value="地区")
    private String area;

    /**
     * 合同url
     */
    @TableField(value = "contract_url")
    @ApiModelProperty(value="合同url")
    private String contractUrl;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 门店类型,1销售门店,2维修仓库，3销售及维修
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value="门店类型,1销售门店,2维修仓库，3销售及维修")
    private String type;

    /**
     * 是否同步
     */
    @TableField(value = "synchronize_flag")
    @ApiModelProperty(value="是否同步")
    private Boolean synchronizeFlag;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value="乐观锁")
    private Integer revision;

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
     * 冗余字段1
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段1")
    private String def1;

    /**
     * 冗余字段2
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段2")
    private String def2;

    /**
     * 冗余字段3
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段3")
    private String def3;

    /**
     * 冗余字段4
     */
    @TableField(value = "def4")
    @ApiModelProperty(value="冗余字段4")
    private String def4;

    /**
     * 冗余字段5
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段5")
    private String def5;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_CODE = "code";

    public static final String COL_NAME = "name";

    public static final String COL_LOGO_URL = "logo_url";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_TEL = "tel";

    public static final String COL_EMAIL = "email";

    public static final String COL_ADDRESS = "address";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_CP = "cp";

    public static final String COL_CITY = "city";

    public static final String COL_AREA = "area";

    public static final String COL_CONTRACT_URL = "contract_url";

    public static final String COL_REMARK = "remark";

    public static final String COL_TYPE = "type";

    public static final String COL_SYNCHRONIZE_FLAG = "synchronize_flag";

    public static final String COL_REVISION = "revision";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF4 = "def4";

    public static final String COL_DEF5 = "def5";
}