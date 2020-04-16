package com.redescooter.ses.mobile.rps.dm;

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
 * 物料表
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeParts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeParts implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_TENANT_ID = "tenant_id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_IMPORT_LOT = "import_lot";
    public static final String COL_STATUS = "status";
    public static final String COL_PARTS_DRAFT_ID = "parts_draft_id";
    public static final String COL_PARTS_TYPE = "parts_type";
    public static final String COL_SEC = "sec";
    public static final String COL_PARTS_NUMBER = "parts_number";
    public static final String COL_CN_NAME = "cn_name";
    public static final String COL_FR_NAME = "fr_name";
    public static final String COL_EN_NAME = "en_name";
    public static final String COL_SN_CLASS = "sn_class";
    public static final String COL_PARTS_QTY = "parts_qty";
    public static final String COL_PRODUCTION_CYCLE = "production_cycle";
    public static final String COL_COST = "cost";
    public static final String COL_SUPPLIER_ID = "supplier_id";
    public static final String COL_DWG = "dwg";
    public static final String COL_NOTE = "note";
    public static final String COL_REVISION = "revision";
    public static final String COL_CREATED_BY = "created_by";
    public static final String COL_CREATED_TIME = "created_time";
    public static final String COL_UPDATED_BY = "updated_by";
    public static final String COL_UPDATED_TIME = "updated_time";
    public static final String COL_ID_CLASS = "id_class";
    public static final String COL_SYNCHRONIZE_FLAG = "synchronize_flag";
    public static final String COL_DEF1 = "def1";
    public static final String COL_DEF2 = "def2";
    public static final String COL_DEF3 = "def3";
    public static final String COL_DEF5 = "def5";
    public static final String COL_DEF6 = "def6";
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 导入批次号
     */
    @ApiModelProperty(value = "导入批次号")
    private String importLot;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 部件草稿主键
     */
    @ApiModelProperty(value = "部件草稿主键")
    private Long partsDraftId;

    /**
     * 类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery
     */
    @ApiModelProperty(value = "类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery")
    private String partsType;

    /**
     * 项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。
     */
    @ApiModelProperty(value = "项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。")
    private String sec;

    /**
     * 部品号
     */
    @ApiModelProperty(value = "部品号")
    private String partsNumber;

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 法文名称
     */
    @ApiModelProperty(value = "法文名称")
    private String frName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 是否可销售 是否可销售,0:SC仅可采购，1:SSC可销售可采购
     */
    @ApiModelProperty(value = "是否可销售 是否可销售,0:SC仅可采购，1:SSC可销售可采购")
    private String snClass;

    /**
     * 部品数量
     */
    @ApiModelProperty(value = "部品数量")
    private Integer partsQty;

    /**
     * 生产周期
     */
    @ApiModelProperty(value = "生产周期")
    private String productionCycle;

    /**
     * 成本
     */
    @ApiModelProperty(value = "成本")
    private String cost;

    /**
     * 供应商
     */
    @ApiModelProperty(value = "供应商")
    private Long supplierId;

    /**
     * 图纸
     */
    @ApiModelProperty(value = "图纸")
    private String dwg;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 是否有唯一编码
     */
    @ApiModelProperty(value = "是否有唯一编码")
    private Boolean idClass;

    /**
     * 是否同步
     */
    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def6;

    private static final long serialVersionUID = 1L;

    public static OpePartsBuilder builder() {
        return new OpePartsBuilder();
    }
}