package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 销售区域表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSaleArea")
@Data
@TableName(value = "ope_sale_area")
public class OpeSaleArea implements Serializable {
    public static final String COL_PARENT_ID = "parent_id";
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键id")
    @TableLogic
    private Long id;

    /**
     * 逻辑删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识")
    @TableLogic
    private Integer dr;

    /**
     * 父级区域id
     */
    @TableField(value = "p_id")
    @ApiModelProperty(value = "父级区域id")
    private Long pId;

    /**
     * 区域编码
     */
    @TableField(value = "area_code")
    @ApiModelProperty(value = "区域编码")
    private String areaCode;

    /**
     * 区域名称
     */
    @TableField(value = "area_name")
    @ApiModelProperty(value = "区域名称")
    private String areaName;

    /**
     * 区域等级
     */
    @TableField(value = "level")
    @ApiModelProperty(value = "区域等级")
    private Integer level;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    @ApiModelProperty(value = "经度")
    private String longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value = "纬度")
    private String latitude;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_P_ID = "p_id";

    public static final String COL_AREA_CODE = "area_code";

    public static final String COL_AREA_NAME = "area_name";

    public static final String COL_LEVEL = "level";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATE_TIME = "update_time";
}