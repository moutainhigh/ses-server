package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 组装单质检条目
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeAssemblyQcItem")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_assembly_qc_item")
public class OpeAssemblyQcItem implements Serializable {
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
    @TableLogic
    private Integer dr;

    /**
     * 组装单id
     */
    @TableField(value = "assembly_id")
    @ApiModelProperty(value = "组装单id")
    private Long assemblyId;

    /**
     * 组装单子表Id
     */
    @TableField(value = "assembly_b_id")
    @ApiModelProperty(value = "组装单子表Id")
    private Long assemblyBId;

    /**
     * 质检结果表
     */
    @TableField(value = "assembly_b_qc_id")
    @ApiModelProperty(value = "质检结果表")
    private Long assemblyBQcId;

    /**
     * 产品Id
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品Id")
    private Long productId;

    /**
     * 序列号
     */
    @TableField(value = "serial_num")
    @ApiModelProperty(value = "序列号")
    private String serialNum;

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
     * 创建表
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建表")
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

    public static final String COL_ASSEMBLY_ID = "assembly_id";

    public static final String COL_ASSEMBLY_B_ID = "assembly_b_id";

    public static final String COL_ASSEMBLY_B_QC_ID = "assembly_b_qc_id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_SERIAL_NUM = "serial_num";

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

    public static OpeAssemblyQcItemBuilder builder() {
        return new OpeAssemblyQcItemBuilder();
    }
}