package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产品质检记录表
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeOrderQcTrace")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_order_qc_trace")
public class OpeOrderQcTrace {
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
     * 质检项结果Id
     */
    @TableField(value = "product_qc_template_b_id")
    @ApiModelProperty(value = "质检项结果Id")
    private Long productQcTemplateBId;

    /**
     * 质检结果
     */
    @TableField(value = "product_qc_template_b_name")
    @ApiModelProperty(value = "质检结果")
    private String productQcTemplateBName;

    /**
     * 质检项Id
     */
    @TableField(value = "product_qc_template_id")
    @ApiModelProperty(value = "质检项Id")
    private Long productQcTemplateId;

    /**
     * 质检项名称
     */
    @TableField(value = "product_qc_template_name")
    @ApiModelProperty(value = "质检项名称")
    private String productQcTemplateName;

    /**
     * 商品质检条目
     */
    @TableField(value = "qc_item_id")
    @ApiModelProperty(value = "商品质检条目")
    private Long qcItemId;

    /**
     * 质检图片（多个图片逗号分隔）
     */
    @TableField(value = "picture")
    @ApiModelProperty(value = "质检图片（多个图片逗号分隔）")
    private String picture;

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
    @TableField(value = "def4")
    @ApiModelProperty(value = "冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private Double def5;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_PRODUCT_QC_TEMPLATE_B_ID = "product_qc_template_b_id";

    public static final String COL_PRODUCT_QC_TEMPLATE_B_NAME = "product_qc_template_b_name";

    public static final String COL_PRODUCT_QC_TEMPLATE_ID = "product_qc_template_id";

    public static final String COL_PRODUCT_QC_TEMPLATE_NAME = "product_qc_template_name";

    public static final String COL_QC_ITEM_ID = "qc_item_id";

    public static final String COL_PICTURE = "picture";

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