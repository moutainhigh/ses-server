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
 * 采购单质检记录
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpePurchasQcTrace")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpePurchasQcTrace implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_PART_ID = "part_id";
    public static final String COL_PURCHAS_ID = "purchas_id";
    public static final String COL_PURCHAS_B_QC_ID = "purchas_b_qc_id";
    public static final String COL_PART_QC_TEMPLATE_ID = "part_qc_template_id";
    public static final String COL_PART_QC_TEMPLATE_B_ID = "part_qc_template_b_id";
    public static final String COL_PURCHAS_B_QC_ITEM_ID = "purchas_b_qc_item_id";
    public static final String COL_PART_QC_PICTURE = "part_qc_picture";
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
     * 部件Id
     */
    @ApiModelProperty(value = "部件Id")
    private Long partId;

    /**
     * 采购单id
     */
    @ApiModelProperty(value = "采购单id")
    private Long purchasId;

    /**
     * 采购单子表Id
     */
    @ApiModelProperty(value = "采购单子表Id")
    private Long purchasBQcId;

    /**
     * 质检模板Id
     */
    @ApiModelProperty(value = "质检模板Id")
    private Long partQcTemplateId;

    /**
     * 质检结果Id
     */
    @ApiModelProperty(value = "质检结果Id")
    private String partQcTemplateBId;

    /**
     * 质检条目Id
     */
    @ApiModelProperty(value = "质检条目Id")
    private Long purchasBQcItemId;

    /**
     * 质检图片的逗号分隔
     */
    @ApiModelProperty(value = "质检图片的逗号分隔")
    private String partQcPicture;

    /**
     * 质检结果
     */
    @ApiModelProperty(value = "质检结果")
    private String qcResult;

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

    public static OpePurchasQcTraceBuilder builder() {
        return new OpePurchasQcTraceBuilder();
    }
}