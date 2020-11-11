package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 入库单部件明细表
    */
@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeInWhousePartsB")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_in_whouse_parts_b")
public class OpeInWhousePartsB {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除")
    private Integer dr;

    /**
     * 关联的入库单id
     */
    @TableField(value = "in_wh_id")
    @ApiModelProperty(value="关联的入库单id")
    private Long inWhId;

    /**
     * 部件id
     */
    @TableField(value = "parts_id")
    @ApiModelProperty(value="部件id")
    private Long partsId;

    /**
     * 部件名称(英文名称)
     */
    @TableField(value = "parts_name")
    @ApiModelProperty(value="部件名称(英文名称)")
    private String partsName;

    /**
     * 部件编号
     */
    @TableField(value = "parts_no")
    @ApiModelProperty(value="部件编号")
    private String partsNo;

    /**
     * 部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination
     */
    @TableField(value = "parts_type")
    @ApiModelProperty(value="部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    /**
     * 采购数量
     */
    @TableField(value = "purchase_qty")
    @ApiModelProperty(value="采购数量")
    private Integer purchaseQty;

    /**
     * 可入库数量
     */
    @TableField(value = "able_in_wh_qty")
    @ApiModelProperty(value="可入库数量")
    private Integer ableInWhQty;

    /**
     * 入库数量
     */
    @TableField(value = "in_wh_qty")
    @ApiModelProperty(value="入库数量")
    private Integer inWhQty;

    /**
     * 实际入库数量
     */
    @TableField(value = "act_in_wh_qty")
    @ApiModelProperty(value="实际入库数量")
    private Integer actInWhQty;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

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
    @TableField(value = "def4")
    @ApiModelProperty(value="冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private BigDecimal def5;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_IN_WH_ID = "in_wh_id";

    public static final String COL_PARTS_ID = "parts_id";

    public static final String COL_PARTS_NAME = "parts_name";

    public static final String COL_PARTS_NO = "parts_no";

    public static final String COL_PARTS_TYPE = "parts_type";

    public static final String COL_PURCHASE_QTY = "purchase_qty";

    public static final String COL_ABLE_IN_WH_QTY = "able_in_wh_qty";

    public static final String COL_IN_WH_QTY = "in_wh_qty";

    public static final String COL_ACT_IN_WH_QTY = "act_in_wh_qty";

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