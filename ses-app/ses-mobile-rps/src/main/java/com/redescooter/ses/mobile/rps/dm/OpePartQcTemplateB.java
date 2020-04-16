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
 * 部件质检模板结果项
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpePartQcTemplateB")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpePartQcTemplateB implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_PART_QC_TEMPLATE_ID = "part_qc_template_id";
    public static final String COL_QC_RESULT = "qc_result";
    public static final String COL_UPLOAD_FLAG = "upload_flag";
    public static final String COL_RESULTS_SEQUENCE = "results_sequence";
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
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 质检项Id
     */
    @ApiModelProperty(value = "质检项Id")
    private Long partQcTemplateId;

    /**
     * 质检结果
     */
    @ApiModelProperty(value = "质检结果")
    private String qcResult;

    /**
     * 是否允许上传图片
     */
    @ApiModelProperty(value = "是否允许上传图片")
    private Boolean uploadFlag;

    /**
     * 结果集排序优先级
     */
    @ApiModelProperty(value = "结果集排序优先级")
    private Integer resultsSequence;

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
    private BigDecimal def6;

    private static final long serialVersionUID = 1L;

    public static OpePartQcTemplateBBuilder builder() {
        return new OpePartQcTemplateBBuilder();
    }
}