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
 * @date 2021/2/2 14:51
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeProductionPartsRelation")
@Data
@TableName(value = "operation.ope_production_parts_relation")
public class OpeProductionPartsRelation implements Serializable {
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
     * 关联的对象id（整车的草稿、bom，组装的草稿、bom）
     */
    @TableField(value = "production_id")
    @ApiModelProperty(value = "关联的对象id（整车的草稿、bom，组装的草稿、bom）")
    private Long productionId;

    /**
     * 关联的对象类型，1：整车草稿，2：整车bom，3：组装草稿，4：组装bom
     */
    @TableField(value = "production_type")
    @ApiModelProperty(value = "关联的对象类型，1：整车草稿，2：整车bom，3：组装草稿，4：组装bom")
    private Integer productionType;

    /**
     * 部件id
     */
    @TableField(value = "parts_id")
    @ApiModelProperty(value = "部件id")
    private Long partsId;

    /**
     * 部件编号
     */
    @TableField(value = "parts_no")
    @ApiModelProperty(value = "部件编号")
    private String partsNo;

    /**
     * 部件区域编码id
     */
    @TableField(value = "parts_sec")
    @ApiModelProperty(value = "部件区域编码id")
    private Long partsSec;

    /**
     * 采购周期
     */
    @TableField(value = "procurement_cycle")
    @ApiModelProperty(value = "采购周期")
    private Integer procurementCycle;

    /**
     * 部品数量
     */
    @TableField(value = "parts_qty")
    @ApiModelProperty(value = "部品数量")
    private Integer partsQty;

    /**
     * 中文名称
     */
    @TableField(value = "cn_name")
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 英文名称
     */
    @TableField(value = "en_name")
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 法文名称
     */
    @TableField(value = "fr_name")
    @ApiModelProperty(value = "法文名称")
    private String frName;

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

    public static final String COL_PRODUCTION_ID = "production_id";

    public static final String COL_PRODUCTION_TYPE = "production_type";

    public static final String COL_PARTS_ID = "parts_id";

    public static final String COL_PARTS_NO = "parts_no";

    public static final String COL_PARTS_SEC = "parts_sec";

    public static final String COL_PROCUREMENT_CYCLE = "procurement_cycle";

    public static final String COL_PARTS_QTY = "parts_qty";

    public static final String COL_CN_NAME = "cn_name";

    public static final String COL_EN_NAME = "en_name";

    public static final String COL_FR_NAME = "fr_name";

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