package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeAllocateBTrace")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_allocate_b_trace")
public class OpeAllocateBTrace implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "删除标识")
    private Integer dr;

    /**
     * 调拨单Id
     */
    @TableField(value = "allocate_id")
    @ApiModelProperty(value = "调拨单Id")
    private Long allocateId;

    /**
     * 调拨单子表Id
     */
    @TableField(value = "allocate_b_id")
    @ApiModelProperty(value = "调拨单子表Id")
    private Long allocateBId;

    /**
     * 部件Id
     */
    @TableField(value = "part_id")
    @ApiModelProperty(value = "部件Id")
    private Long partId;

    /**
     * 批次号
     */
    @TableField(value = "batch_no")
    @ApiModelProperty(value = "批次号")
    private String batchNo;

    /**
     * 备料数量
     */
    @TableField(value = "qty")
    @ApiModelProperty(value = "备料数量")
    private Integer qty;

    /**
     * 序列号
     */
    @TableField(value = "serial_num")
    @ApiModelProperty(value = "序列号")
    private String serialNum;

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

    public static final String COL_ALLOCATE_ID = "allocate_id";

    public static final String COL_ALLOCATE_B_ID = "allocate_b_id";

    public static final String COL_PART_ID = "part_id";

    public static final String COL_BATCH_NO = "batch_no";

    public static final String COL_QTY = "qty";

    public static final String COL_SERIAL_NUM = "serial_num";

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

    public static OpeAllocateBTraceBuilder builder() {
        return new OpeAllocateBTraceBuilder();
    }
}