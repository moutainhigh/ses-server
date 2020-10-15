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
 * 产品质检模板表
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeProductionQualityTempate")
@Data
@TableName(value = "ope_production_quality_tempate")
public class OpeProductionQualityTempate implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 关联产品的Id（部件草稿，部件，整车草稿，整车，组装草稿，组装件）
     */
    @TableField(value = "production_id")
    @ApiModelProperty(value = "关联产品的Id（部件草稿，部件，整车草稿，整车，组装草稿，组装件）")
    private Long productionId;

    /**
     * 导入批次号
     */
    @TableField(value = "import_excel_batch_no")
    @ApiModelProperty(value = "导入批次号")
    private String importExcelBatchNo;

    /**
     * 来源方式,(0：手动新增，1：导入)
     */
    @TableField(value = "source_type")
    @ApiModelProperty(value = "来源方式,(0：手动新增，1：导入)")
    private Integer sourceType;

    /**
     * 关联类型,(0：部件草稿，1：部件，2：整车草稿，3：整车，4：组装草稿，5：组装件)
     */
    @TableField(value = "production_type")
    @ApiModelProperty(value = "关联类型,(0：部件草稿，1：部件，2：整车草稿，3：整车，4：组装草稿，5：组装件)")
    private Integer productionType;

    /**
     * 质检项名称
     */
    @TableField(value = "qc_item_name")
    @ApiModelProperty(value = "质检项名称")
    private String qcItemName;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

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

    public static final String COL_IMPORT_EXCEL_BATCH_NO = "import_excel_batch_no";

    public static final String COL_SOURCE_TYPE = "source_type";

    public static final String COL_PRODUCTION_TYPE = "production_type";

    public static final String COL_QC_ITEM_NAME = "qc_item_name";

    public static final String COL_REVISION = "revision";

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
