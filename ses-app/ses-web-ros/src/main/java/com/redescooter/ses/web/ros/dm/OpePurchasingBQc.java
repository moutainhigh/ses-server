package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpePurchasingBQc")
@Data
@TableName(value = "ope_purchasing_b_qc")
public class OpePurchasingBQc implements Serializable {
    public static final String COL_OPE_PURCHASING_B_ID = "ope_purchasing_b_id";
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
    @TableLogic
    @ApiModelProperty(value = "删除标志")
    private Integer dr;

    /**
     * 租户Id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    /**
     * userId
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "userId")
    private Integer userId;

    /**
     * 采购单条目Id
     */
    @TableField(value = "purchasing_b_id")
    @ApiModelProperty(value = "采购单条目Id")
    private Long purchasingBId;

    /**
     * 质检人Id
     */
    @TableField(value = "quality_inspector_id")
    @ApiModelProperty(value = "质检人Id")
    private Integer qualityInspectorId;

    /**
     * 质检批次号
     */
    @TableField(value = "batch_no")
    @ApiModelProperty(value = "质检批次号")
    private String batchNo;

    /**
     * 质检状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "质检状态")
    private String status;

    /**
     * 需要质检总数
     */
    @TableField(value = "total_quality_inspected")
    @ApiModelProperty(value = "需要质检总数")
    private Integer totalQualityInspected;

    /**
     * 质检通过数量
     */
    @TableField(value = "pass_count")
    @ApiModelProperty(value = "质检通过数量")
    private Integer passCount;

    /**
     * 质检失败数量
     */
    @TableField(value = "fail_count")
    @ApiModelProperty(value = "质检失败数量")
    private Integer failCount;

    /**
     * 质检时间
     */
    @TableField(value = "quality_inspection_time")
    @ApiModelProperty(value = "质检时间")
    private Date qualityInspectionTime;

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

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_PURCHASING_B_ID = "purchasing_b_id";

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
}