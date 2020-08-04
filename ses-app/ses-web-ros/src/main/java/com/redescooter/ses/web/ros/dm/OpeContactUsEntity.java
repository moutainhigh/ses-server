package com.redescooter.ses.web.ros.dm;

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
    * 官网联系我们
    */
@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeContactUsEntity")
@Data
@TableName(value = "ope_contact_us")
public class OpeContactUsEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 删除标志
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="删除标志")
    private Integer dr;

    /**
     * 客户邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value="客户邮箱")
    private String email;

    /**
     * 名
     */
    @TableField(value = "first_name")
    @ApiModelProperty(value="名")
    private String firstName;

    /**
     * 姓
     */
    @TableField(value = "last_name")
    @ApiModelProperty(value="姓")
    private String lastName;

    /**
     * 全名
     */
    @TableField(value = "full_name")
    @ApiModelProperty(value="全名")
    private String fullName;

    /**
     * 电话
     */
    @TableField(value = "telephone")
    @ApiModelProperty(value="电话")
    private String telephone;

    /**
     * 国家Id
     */
    @TableField(value = "country")
    @ApiModelProperty(value="国家Id")
    private Long country;

    /**
     * 国家代码
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value="国家代码")
    private String countryCode;

    /**
     * 国家名称
     */
    @TableField(value = "country_name")
    @ApiModelProperty(value="国家名称")
    private String countryName;

    /**
     * 城市名称
     */
    @TableField(value = "city_name")
    @ApiModelProperty(value="城市名称")
    private String cityName;

    /**
     * 区域Id
     */
    @TableField(value = "district")
    @ApiModelProperty(value="区域Id")
    private Long district;

    /**
     * 区域编码
     */
    @TableField(value = "district_name")
    @ApiModelProperty(value="区域编码")
    private String districtName;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 联系次数
     */
    @TableField(value = "frequency")
    @ApiModelProperty(value="联系次数")
    private Integer frequency;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

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

    public static final String COL_EMAIL = "email";

    public static final String COL_FIRST_NAME = "first_name";

    public static final String COL_LAST_NAME = "last_name";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_COUNTRY = "country";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_COUNTRY_NAME = "country_name";

    public static final String COL_CITY_NAME = "city_name";

    public static final String COL_DISTRICT = "district";

    public static final String COL_DISTRICT_NAME = "district_name";

    public static final String COL_ADDRESS = "address";

    public static final String COL_FREQUENCY = "frequency";

    public static final String COL_STATUS = "status";

    public static final String COL_REMARK = "remark";

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