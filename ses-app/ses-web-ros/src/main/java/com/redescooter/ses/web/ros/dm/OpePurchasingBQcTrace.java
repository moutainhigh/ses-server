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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpePurchasingBQcTrace")
@Data
@TableName(value = "ope_purchasing_b_qc_trace")
public class OpePurchasingBQcTrace implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除表示
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除表示")
    private Integer dr;

    /**
     * 租户Id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    /**
     * 用户Id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户Id")
    private Long userId;

    /**
     * 采购单条目Id
     */
    @TableField(value = "purchasing_b_id")
    @ApiModelProperty(value = "采购单条目Id")
    private Long purchasingBId;

    /**
     * 部品质检Id
     */
    @TableField(value = "qc_b_id")
    @ApiModelProperty(value = "部品质检Id")
    private Long qcBId;

    /**
     * 部品Id
     */
    @TableField(value = "parts_id")
    @ApiModelProperty(value = "部品Id")
    private Long partsId;

    /**
     * 批次号
     */
    @TableField(value = "batch_no")
    @ApiModelProperty(value = "批次号")
    private String batchNo;

    /**
     * 质检人
     */
    @TableField(value = "quality_inspector_id")
    @ApiModelProperty(value = "质检人")
    private Long qualityInspectorId;

    /**
     * 质检状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "质检状态")
    private String status;

    /**
     * 质检时间
     */
    @TableField(value = "quality_inspected_time")
    @ApiModelProperty(value = "质检时间")
    private Date qualityInspectedTime;

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
     * 创建时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "创建时间")
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

    public static final String COL_QC_B_ID = "qc_b_id";

    public static final String COL_PARTS_ID = "parts_id";

    public static final String COL_BATCH_NO = "batch_no";

    public static final String COL_QUALITY_INSPECTOR_ID = "quality_inspector_id";

    public static final String COL_STATUS = "status";

    public static final String COL_QUALITY_INSPECTED_TIME = "quality_inspected_time";

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