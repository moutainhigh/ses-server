package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 发货单车辆产品明细表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeInvoiceScooterDetailB")
@Data
@TableName(value = "ope_invoice_scooter_detail_b")
public class OpeInvoiceScooterDetailB implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 发货单id
     */
    @TableField(value = "invoice_id")
    @ApiModelProperty(value = "发货单id")
    private Long invoiceId;

    /**
     * 车型（规格分组）的id
     */
    @TableField(value = "group_id")
    @ApiModelProperty(value = "车型（规格分组）的id")
    private Long groupId;

    /**
     * 颜色id
     */
    @TableField(value = "color_id")
    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    /**
     * 数量（1）
     */
    @TableField(value = "qty")
    @ApiModelProperty(value = "数量（1）")
    private Integer qty;

    /**
     * 唯一码
     */
    @TableField(value = "unique_code")
    @ApiModelProperty(value = "唯一码")
    private String uniqueCode;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

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
    private BigDecimal def5;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_INVOICE_ID = "invoice_id";

    public static final String COL_GROUP_ID = "group_id";

    public static final String COL_COLOR_ID = "color_id";

    public static final String COL_QTY = "qty";

    public static final String COL_UNIQUE_CODE = "unique_code";

    public static final String COL_REMARK = "remark";

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