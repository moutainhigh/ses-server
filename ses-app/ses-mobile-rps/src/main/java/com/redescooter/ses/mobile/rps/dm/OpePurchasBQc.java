package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 采购条目QC质检
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpePurchasBQc")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpePurchasBQc implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_TENANT_ID = "tenant_id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_PURCHAS_B_ID = "purchas_b_id";
    public static final String COL_PARTS_ID = "parts_id";
    public static final String COL_QUALITY_INSPECTOR_ID = "quality_inspector_id";
    public static final String COL_BATCH_NO = "batch_no";
    public static final String COL_STATUS = "status";
    public static final String COL_TOTAL_QUALITY_INSPECTED = "total_quality_inspected";
    public static final String COL_PASS_COUNT = "pass_count";
    public static final String COL_FAIL_COUNT = "fail_count";
    public static final String COL_QUALITY_INSPECTION_TIME = "quality_inspection_time";
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
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private Integer dr;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    /**
     * userId
     */
    @ApiModelProperty(value = "userId")
    private Long userId;

    /**
     * 采购单条目Id
     */
    @ApiModelProperty(value = "采购单条目Id")
    private Long purchasBId;

    /**
     * 部品Id
     */
    @ApiModelProperty(value = "部品Id")
    private Long partsId;

    /**
     * 质检人Id
     */
    @ApiModelProperty(value = "质检人Id")
    private Long qualityInspectorId;

    /**
     * 质检批次号
     */
    @ApiModelProperty(value = "质检批次号")
    private String batchNo;

    /**
     * 质检状态
     */
    @ApiModelProperty(value = "质检状态")
    private String status;

    /**
     * 需要质检总数
     */
    @ApiModelProperty(value = "需要质检总数")
    private Integer totalQualityInspected;

    /**
     * 质检通过数量
     */
    @ApiModelProperty(value = "质检通过数量")
    private Integer passCount;

    /**
     * 质检失败数量
     */
    @ApiModelProperty(value = "质检失败数量")
    private Integer failCount;

    /**
     * 质检时间
     */
    @ApiModelProperty(value = "质检时间")
    private Date qualityInspectionTime;

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
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static OpePurchasBQcBuilder builder() {
        return new OpePurchasBQcBuilder();
    }
}