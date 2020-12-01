package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售车辆表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSaleScooter")
@Data
@TableName(value = "operation.ope_sale_scooter")
public class OpeSaleScooter {
    /**
     * 主键
     */
    @TableId(value = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 所属部门id
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "所属部门id")
    private Long deptId;

    /**
     * 产品名称
     */
    @TableField(value = "product_name")
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 产品编码
     */
    @TableField(value = "product_code")
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 车辆所属规格id
     */
    @TableField(value = "specificat_id")
    @ApiModelProperty(value = "车辆所属规格id")
    private Long specificatId;

    /**
     * 车辆所属分组id
     */
    @TableField(value = "group_id")
    @ApiModelProperty(value = "车辆所属分组id")
    private Long groupId;

    /**
     * 车辆所属颜色id
     */
    @TableField(value = "color_id")
    @ApiModelProperty(value = "车辆所属颜色id")
    private Long colorId;

    /**
     * 销售状态，0：不可销售，1：可销售
     */
    @TableField(value = "sale_stutas")
    @ApiModelProperty(value = "销售状态，0：不可销售，1：可销售")
    private Integer saleStutas;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

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
    @TableField(value = "def4")
    @ApiModelProperty(value = "冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_PRODUCT_NAME = "product_name";

    public static final String COL_PRODUCT_CODE = "product_code";

    public static final String COL_SPECIFICAT_ID = "specificat_id";

    public static final String COL_GROUP_ID = "group_id";

    public static final String COL_COLOR_ID = "color_id";

    public static final String COL_SALE_STUTAS = "sale_stutas";

    public static final String COL_REMARK = "remark";

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