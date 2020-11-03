package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 质检条目
    */
@ApiModel(value="com-redescooter-ses-mobile-rps-dm-OpeOrderQcItem")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_order_qc_item")
public class OpeOrderQcItem {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="删除标识")
    private Integer dr;

    /**
     * 订单序列号绑表Id
     */
    @TableField(value = "order_serial_id")
    @ApiModelProperty(value="订单序列号绑表Id")
    private Long orderSerialId;

    /**
     * 序列号
     */
    @TableField(value = "serial_num")
    @ApiModelProperty(value="序列号")
    private String serialNum;

    /**
     * 批次号
     */
    @TableField(value = "batch_no")
    @ApiModelProperty(value="批次号")
    private String batchNo;

    /**
     * 质检结果1、PASS 2 NG
     */
    @TableField(value = "qc_result")
    @ApiModelProperty(value="质检结果1、PASS 2 NG")
    private Integer qcResult;

    /**
     * 附件 多个附件逗号分隔
     */
    @TableField(value = "annex")
    @ApiModelProperty(value="附件 多个附件逗号分隔")
    private String annex;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value="乐观锁")
    private Integer revision;

    /**
     * 创建表
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建表")
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
    private Double def5;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_ORDER_SERIAL_ID = "order_serial_id";

    public static final String COL_SERIAL_NUM = "serial_num";

    public static final String COL_BATCH_NO = "batch_no";

    public static final String COL_QC_RESULT = "qc_result";

    public static final String COL_ANNEX = "annex";

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