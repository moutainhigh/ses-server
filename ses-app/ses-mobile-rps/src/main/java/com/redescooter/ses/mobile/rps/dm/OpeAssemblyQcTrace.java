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
 * 组装单质检记录
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeAssemblyQcTrace")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeAssemblyQcTrace implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_OPE_ASSEMBLY_B_ID = "ope_assembly_b_id";
    public static final String COL_PRODUCT_QC_TEMPLATE_B_ID = "product_qc_template_b_id";
    public static final String COL_QC_RESULT = "qc_result";
    public static final String COL_PICTURE = "picture";
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
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer dr;

    /**
     * 组装单子表id
     */
    @ApiModelProperty(value = "组装单子表id")
    private Long opeAssemblyBId;

    /**
     * 质检项Id
     */
    @ApiModelProperty(value = "质检项Id")
    private Long productQcTemplateBId;

    /**
     * 质检结果
     */
    @ApiModelProperty(value = "质检结果")
    private String qcResult;

    /**
     * 质检图片（多个图片逗号分隔）
     */
    @ApiModelProperty(value = "质检图片（多个图片逗号分隔）")
    private String picture;

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

    public static OpeAssemblyQcTraceBuilder builder() {
        return new OpeAssemblyQcTraceBuilder();
    }
}