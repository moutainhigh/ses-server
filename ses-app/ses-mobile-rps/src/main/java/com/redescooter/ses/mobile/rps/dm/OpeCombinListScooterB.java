package com.redescooter.ses.mobile.rps.dm;

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

/**
 * @author assert
 * @date 2021/2/2 13:52
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeCombinListScooterB")
@Data
@TableName(value = "ope_combin_list_scooter_b")
public class OpeCombinListScooterB implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 关联的组装单id
     */
    @TableField(value = "combin_id")
    @ApiModelProperty(value = "关联的组装单id")
    private Long combinId;

    /**
     * 车型（规格分组）的id
     */
    @TableField(value = "group_id")
    @ApiModelProperty(value = "车型（规格分组）的id")
    private Long groupId;

    /**
     * 颜色id
     */
    @TableField(value = "color_id")
    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    /**
     * bom车辆id
     */
    @TableField(value = "scooter_bom_id")
    @ApiModelProperty(value = "bom车辆id")
    private Long scooterBomId;

    /**
     * 组装清单状态 0待组装 1已组装
     */
    @TableField(value = "combin_list_status")
    @ApiModelProperty(value = "组装清单状态 0待组装 1已组装")
    private Integer combinListStatus;

    /**
     * 车辆所需部件总数量
     */
    @TableField(value = "qty")
    @ApiModelProperty(value = "车辆所需部件总数量")
    private Integer qty;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_COMBIN_ID = "combin_id";

    public static final String COL_GROUP_ID = "group_id";

    public static final String COL_COLOR_ID = "color_id";

    public static final String COL_SCOOTER_BOM_ID = "scooter_bom_id";

    public static final String COL_COMBIN_LIST_STATUS = "combin_list_status";

    public static final String COL_QTY = "qty";

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
