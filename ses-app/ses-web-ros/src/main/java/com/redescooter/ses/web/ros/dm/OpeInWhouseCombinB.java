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
    * 入库单组装件明细表
    */
@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeInWhouseCombinB")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_in_whouse_combin_b")
public class OpeInWhouseCombinB {
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
     * 组装件名称(英文名称)
     */
    @TableField(value = "combin_name")
    @ApiModelProperty(value="组装件名称(英文名称)")
    private String combinName;

    /**
     * 组装件编号
     */
    @TableField(value = "combin_no")
    @ApiModelProperty(value="组装件编号")
    private String combinNo;

    /**
     * 组装件id
     */
    @TableField(value = "production_combin_bom_id")
    @ApiModelProperty(value="组装件id")
    private Long productionCombinBomId;

    /**
     * 组装数量
     */
    @TableField(value = "combin_qty")
    @ApiModelProperty(value="组装数量")
    private Integer combinQty;

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

    public static final String COL_COMBIN_NAME = "combin_name";

    public static final String COL_COMBIN_NO = "combin_no";

    public static final String COL_PRODUCTION_COMBIN_BOM_ID = "production_combin_bom_id";

    public static final String COL_COMBIN_QTY = "combin_qty";

    public static final String COL_ABLE_IN_WH_QTY = "able_in_wh_qty";

    public static final String COL_IN_WH_QTY = "in_wh_qty";

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