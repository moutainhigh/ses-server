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

@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpePurchasQcTrace")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_purchas_qc_trace")
public class OpePurchasQcTrace implements Serializable {
    public static final String COL_QC_RESULT = "qc_result";
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
     * 部件Id
     */
    @TableField(value = "part_id")
    @ApiModelProperty(value = "部件Id")
    private Long partId;

    /**
     * 采购单id
     */
    @TableField(value = "purchas_id")
    @ApiModelProperty(value = "采购单id")
    private Long purchasId;

    /**
     * 采购单子表Id
     */
    @TableField(value = "purchas_b_qc_id")
    @ApiModelProperty(value = "采购单子表Id")
    private Long purchasBQcId;

    /**
     * 质检模板Id
     */
    @TableField(value = "part_qc_template_id")
    @ApiModelProperty(value = "质检模板Id")
    private Long partQcTemplateId;

    /**
     * 模板质检项名称
     */
    @TableField(value = "part_qc_template_name")
    @ApiModelProperty(value = "模板质检项名称")
    private String partQcTemplateName;

    /**
     * 质检结果Id
     */
    @TableField(value = "part_qc_template_b_id")
    @ApiModelProperty(value = "质检结果Id")
    private Long partQcTemplateBId;

    /**
     * 模板质检结果名称
     */
    @TableField(value = "part_qc_template_b_name")
    @ApiModelProperty(value = "模板质检结果名称")
    private String partQcTemplateBName;

    /**
     * 质检条目Id
     */
    @TableField(value = "purchas_b_qc_item_id")
    @ApiModelProperty(value = "质检条目Id")
    private Long purchasBQcItemId;

    /**
     * 质检图片的逗号分隔
     */
    @TableField(value = "part_qc_picture")
    @ApiModelProperty(value = "质检图片的逗号分隔")
    private String partQcPicture;

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

    public static final String COL_PART_ID = "part_id";

    public static final String COL_PURCHAS_ID = "purchas_id";

    public static final String COL_PURCHAS_B_QC_ID = "purchas_b_qc_id";

    public static final String COL_PART_QC_TEMPLATE_ID = "part_qc_template_id";

    public static final String COL_PART_QC_TEMPLATE_NAME = "part_qc_template_name";

    public static final String COL_PART_QC_TEMPLATE_B_ID = "part_qc_template_b_id";

    public static final String COL_PART_QC_TEMPLATE_B_NAME = "part_qc_template_b_name";

    public static final String COL_PURCHAS_B_QC_ITEM_ID = "purchas_b_qc_item_id";

    public static final String COL_PART_QC_PICTURE = "part_qc_picture";

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

    public static OpePurchasQcTraceBuilder builder() {
        return new OpePurchasQcTraceBuilder();
    }
}
