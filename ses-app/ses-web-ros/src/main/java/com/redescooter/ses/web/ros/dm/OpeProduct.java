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
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeProduct")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_product")
public class OpeProduct implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 产品编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value = "产品编码")
    private String code;

    /**
     * 名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 产品状态:Shelf上架，Obtained下架(默认)
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "产品状态:Shelf上架，Obtained下架(默认)")
    private String status;

    /**
     * 产品类型主键
     */
    @TableField(value = "product_type_id")
    @ApiModelProperty(value = "产品类型主键")
    private Long productTypeId;

    /**
     * SCOOTER;SPAIRPART
     */
    @TableField(value = "product_type_code")
    @ApiModelProperty(value = "SCOOTER;SPAIRPART")
    private String productTypeCode;

    /**
     * 照片
     */
    @TableField(value = "picture")
    @ApiModelProperty(value = "照片")
    private String picture;

    /**
     * 型号
     */
    @TableField(value = "model")
    @ApiModelProperty(value = "型号")
    private String model;

    /**
     * 颜色
     */
    @TableField(value = "color")
    @ApiModelProperty(value = "颜色")
    private String color;

    /**
     * 备注
     */
    @TableField(value = "memo")
    @ApiModelProperty(value = "备注")
    private String memo;

    /**
     * 产品参数 存储JSON
     */
    @TableField(value = "mater_parameter")
    @ApiModelProperty(value = "产品参数 存储JSON")
    private String materParameter;

    /**
     * 其它参数 存储JSON
     */
    @TableField(value = "other_parameter")
    @ApiModelProperty(value = "其它参数 存储JSON")
    private String otherParameter;

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

    public static final String COL_CODE = "code";

    public static final String COL_NAME = "name";

    public static final String COL_STATUS = "status";

    public static final String COL_PRODUCT_TYPE_ID = "product_type_id";

    public static final String COL_PRODUCT_TYPE_CODE = "product_type_code";

    public static final String COL_PICTURE = "picture";

    public static final String COL_MODEL = "model";

    public static final String COL_COLOR = "color";

    public static final String COL_MEMO = "memo";

    public static final String COL_MATER_PARAMETER = "mater_parameter";

    public static final String COL_OTHER_PARAMETER = "other_parameter";

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