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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeRepairShop")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_repair_shop")
public class OpeRepairShop implements Serializable {
    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 所属部门ID,已作废
     */
    @TableField(value = "org_department_id")
    @ApiModelProperty(value = "所属部门ID,已作废")
    private Long orgDepartmentId;

    /**
     * 名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 商家图片
     */
    @TableField(value = "picture1")
    @ApiModelProperty(value = "商家图片")
    private String picture1;

    /**
     * 商家图片2
     */
    @TableField(value = "picture2")
    @ApiModelProperty(value = "商家图片2")
    private String picture2;

    /**
     * 商家图片三
     */
    @TableField(value = "picture3")
    @ApiModelProperty(value = "商家图片三")
    private String picture3;

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
     * SELF_OPERATED 自营, PARTNER合作方
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "SELF_OPERATED 自营, PARTNER合作方")
    private String type;

    /**
     * 营业执照
     */
    @TableField(value = "business_license")
    @ApiModelProperty(value = "营业执照")
    private String businessLicense;

    /**
     * 合同图片
     */
    @TableField(value = "contact_attachment")
    @ApiModelProperty(value = "合同图片")
    private String contactAttachment;

    /**
     * 配送范围 单位千米
     */
    @TableField(value = "service_range")
    @ApiModelProperty(value = "配送范围 单位千米")
    private Long serviceRange;

    /**
     * 服务费用 单位欧元
     */
    @TableField(value = "service_cost")
    @ApiModelProperty(value = "服务费用 单位欧元")
    private Long serviceCost;

    /**
     * 国家
     */
    @TableField(value = "country_id")
    @ApiModelProperty(value = "国家")
    private Integer countryId;

    /**
     * 城市
     */
    @TableField(value = "city_id")
    @ApiModelProperty(value = "城市")
    private Long cityId;

    /**
     * 区域Id
     */
    @TableField(value = "area_id")
    @ApiModelProperty(value = "区域Id")
    private Integer areaId;

    /**
     * 前台解析地址所用参数
     */
    @TableField(value = "place_id")
    @ApiModelProperty(value = "前台解析地址所用参数")
    private String placeId;

    /**
     * 联系电话
     */
    @TableField(value = "contact_number")
    @ApiModelProperty(value = "联系电话")
    private String contactNumber;

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

    public static final String COL_ID = "ID";

    public static final String COL_DR = "dr";

    public static final String COL_ORG_DEPARTMENT_ID = "org_department_id";

    public static final String COL_NAME = "name";

    public static final String COL_ADDRESS = "address";

    public static final String COL_PICTURE1 = "picture1";

    public static final String COL_PICTURE2 = "picture2";

    public static final String COL_PICTURE3 = "picture3";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_TYPE = "type";

    public static final String COL_BUSINESS_LICENSE = "business_license";

    public static final String COL_CONTACT_ATTACHMENT = "contact_attachment";

    public static final String COL_SERVICE_RANGE = "service_range";

    public static final String COL_SERVICE_COST = "service_cost";

    public static final String COL_COUNTRY_ID = "country_id";

    public static final String COL_CITY_ID = "city_id";

    public static final String COL_AREA_ID = "area_id";

    public static final String COL_PLACE_ID = "place_id";

    public static final String COL_CONTACT_NUMBER = "contact_number";

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