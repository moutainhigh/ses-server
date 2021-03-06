package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpePurchasBQcItem")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_purchas_b_qc_item")
public class OpePurchasBQcItem implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 删除标志
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "删除标志")
    @TableLogic
    private Integer dr;

    /**
     * 采购单子表Id
     */
    @TableField(value = "purchas_b_id")
    @ApiModelProperty(value = "采购单子表Id")
    private Long purchasBId;

    /**
     * 部品Id
     */
    @TableField(value = "part_id")
    @ApiModelProperty(value = "部品Id")
    private Long partId;

    /**
     * 质检结果Id
     */
    @TableField(value = "purchas_b_qc_id")
    @ApiModelProperty(value = "质检结果Id")
    private Long purchasBQcId;

    /**
     * 质检批次记录Id
     */
    @TableField(value = "purchas_b_lot_trace_id")
    @ApiModelProperty(value = "质检批次记录Id")
    private Long purchasBLotTraceId;

    /**
     * 批次号
     */
    @TableField(value = "batch_no")
    @ApiModelProperty(value = "批次号")
    private String batchNo;

    /**
     * 部品序列号
     */
    @TableField(value = "serial_num")
    @ApiModelProperty(value = "部品序列号")
    private String serialNum;

    /**
     * 批量质检数量 和质检方式连用
     */
    @TableField(value = "qc_batch_total")
    @ApiModelProperty(value = "批量质检数量 和质检方式连用")
    private Integer qcBatchTotal;

    /**
     * 质检结果
     */
    @TableField(value = "qc_result")
    @ApiModelProperty(value = "质检结果")
    private String qcResult;

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

    public static final String COL_PURCHAS_B_ID = "purchas_b_id";

    public static final String COL_PART_ID = "part_id";

    public static final String COL_PURCHAS_B_QC_ID = "purchas_b_qc_id";

    public static final String COL_PURCHAS_B_LOT_TRACE_ID = "purchas_b_lot_trace_id";

    public static final String COL_BATCH_NO = "batch_no";

    public static final String COL_SERIAL_NUM = "serial_num";

    public static final String COL_QC_BATCH_TOTAL = "qc_batch_total";

    public static final String COL_QC_RESULT = "qc_result";

    public static final String COL_REVISION = "revision";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

    public static OpePurchasBQcItemBuilder builder() {
        return new OpePurchasBQcItemBuilder();
    }
}
