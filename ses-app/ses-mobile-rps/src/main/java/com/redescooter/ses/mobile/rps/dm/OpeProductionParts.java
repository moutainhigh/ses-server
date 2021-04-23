package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author assert
 * @date 2021/1/29 11:29
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeProductionParts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_production_parts")
public class OpeProductionParts implements Serializable {
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
    @TableLogic
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 部门id（做数据权限用）
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "部门id（做数据权限用）")
    private Long deptId;

    /**
     * 部件编号
     */
    @TableField(value = "parts_no")
    @ApiModelProperty(value = "部件编号")
    private String partsNo;

    /**
     * 部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination 6：ECU
     */
    @TableField(value = "parts_type")
    @ApiModelProperty(value = "部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination 6：ECU")
    private Integer partsType;

    /**
     * 部件区域编码id
     */
    @TableField(value = "parts_sec")
    @ApiModelProperty(value = "部件区域编码id")
    private Long partsSec;

    /**
     * 是否禁用，0：否，1：是
     */
    @TableField(value = "disable")
    @ApiModelProperty(value = "是否禁用，0：否，1：是")
    private Integer disable;

    /**
     * 是否可采购，0：否，1：是
     */
    @TableField(value = "sn_class")
    @ApiModelProperty(value = "是否可采购，0：否，1：是")
    private Integer snClass;

    /**
     * 是否有序列号，0：否，1：是
     */
    @TableField(value = "id_calss")
    @ApiModelProperty(value = "是否有序列号，0：否，1：是")
    private Integer idCalss;

    /**
     * 是否是组装件，0：否，1：是
     */
    @TableField(value = "parts_is_assembly")
    @ApiModelProperty(value = "是否是组装件，0：否，1：是")
    private Integer partsIsAssembly;

    /**
     * 是否可用于组装件，0：否，1：是
     */
    @TableField(value = "parts_is_for_assembly")
    @ApiModelProperty(value = "是否可用于组装件，0：否，1：是")
    private Integer partsIsForAssembly;

    /**
     * 部品数量
     */
    @TableField(value = "parts_qty")
    @ApiModelProperty(value = "部品数量")
    private Integer partsQty;

    /**
     * 供应商id
     */
    @TableField(value = "supplier_id")
    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    /**
     * 采购周期
     */
    @TableField(value = "procurement_cycle")
    @ApiModelProperty(value = "采购周期")
    private Integer procurementCycle;

    /**
     * 图纸
     */
    @TableField(value = "dwg")
    @ApiModelProperty(value = "图纸")
    private String dwg;

    /**
     * 是否有质检模板，0：否，1：是
     */
    @TableField(value = "qc_flag")
    @ApiModelProperty(value = "是否有质检模板，0：否，1：是")
    private Boolean qcFlag;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 发布人id
     */
    @TableField(value = "announ_user_id")
    @ApiModelProperty(value = "发布人id")
    private Long announUserId;

    /**
     * 操作发布人id
     */
    @TableField(value = "op_announ_user_id")
    @ApiModelProperty(value = "操作发布人id")
    private Long opAnnounUserId;

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
     * 级别
     */
    @TableField(value = "level")
    @ApiModelProperty(value = "级别")
    private Integer level;

    /**
     * 母图纸
     */
    @TableField(value = "main_drawing")
    @ApiModelProperty(value = "母图纸")
    private String mainDrawing;

    /**
     * 设变号
     */
    @TableField(value = "ecn_number")
    @ApiModelProperty(value = "设变号")
    private String ecnNumber;

    /**
     * 品目
     */
    @TableField(value = "item")
    @ApiModelProperty(value = "品目")
    private String item;

    /**
     * 销售分区
     */
    @TableField(value = "sell_calss")
    @ApiModelProperty(value = "销售分区")
    private String sellCalss;

    /**
     * 图纸尺寸
     */
    @TableField(value = "drawing_size")
    @ApiModelProperty(value = "图纸尺寸")
    private String drawingSize;

    /**
     * 重量
     */
    @TableField(value = "weight")
    @ApiModelProperty(value = "重量")
    private Double weight;

    /**
     * 供应商2
     */
    @TableField(value = "supplier_id_2")
    @ApiModelProperty(value = "供应商2")
    private Long supplierId2;

    /**
     * 依存度区分
     */
    @TableField(value = "rate_typ")
    @ApiModelProperty(value = "依存度区分")
    private String rateTyp;

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

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_PARTS_NO = "parts_no";

    public static final String COL_PARTS_TYPE = "parts_type";

    public static final String COL_PARTS_SEC = "parts_sec";

    public static final String COL_DISABLE = "disable";

    public static final String COL_SN_CLASS = "sn_class";

    public static final String COL_ID_CALSS = "id_calss";

    public static final String COL_PARTS_IS_ASSEMBLY = "parts_is_assembly";

    public static final String COL_PARTS_IS_FOR_ASSEMBLY = "parts_is_for_assembly";

    public static final String COL_PARTS_QTY = "parts_qty";

    public static final String COL_SUPPLIER_ID = "supplier_id";

    public static final String COL_PROCUREMENT_CYCLE = "procurement_cycle";

    public static final String COL_DWG = "dwg";

    public static final String COL_QC_FLAG = "qc_flag";

    public static final String COL_REMARK = "remark";

    public static final String COL_ANNOUN_USER_ID = "announ_user_id";

    public static final String COL_OP_ANNOUN_USER_ID = "op_announ_user_id";

    public static final String COL_CN_NAME = "cn_name";

    public static final String COL_EN_NAME = "en_name";

    public static final String COL_FR_NAME = "fr_name";

    public static final String COL_LEVEL = "level";

    public static final String COL_MAIN_DRAWING = "main_drawing";

    public static final String COL_ECN_NUMBER = "ecn_number";

    public static final String COL_ITEM = "item";

    public static final String COL_SELL_CALSS = "sell_calss";

    public static final String COL_DRAWING_SIZE = "drawing_size";

    public static final String COL_WEIGHT = "weight";

    public static final String COL_SUPPLIER_ID_2 = "supplier_id_2";

    public static final String COL_RATE_TYP = "rate_typ";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF4 = "def4";

    public static final String COL_DEF5 = "def5";

    public static OpeProductionPartsBuilder builder() {
        return new OpeProductionPartsBuilder();
    }
}
