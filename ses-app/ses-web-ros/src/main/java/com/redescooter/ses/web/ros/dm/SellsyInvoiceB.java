package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-admin-dev-controller-SellsyInvoiceB")
@Data
@Builder
@TableName(value = "sellsy_invoice_b")
public class SellsyInvoiceB implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 发票Id
     */
    @TableField(value = "invoice_id")
    @ApiModelProperty(value = "发票Id")
    private Long invoiceId;

    /**
     * 产品名称
     */
    @TableField(value = "product_name")
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 产品编号
     */
    @TableField(value = "product_num")
    @ApiModelProperty(value = "产品编号")
    private String productNum;

    /**
     * tva增值税
     */
    @TableField(value = "tva")
    @ApiModelProperty(value = "tva增值税")
    private Integer tva;

    /**
     * 产品数量
     */
    @TableField(value = "qty")
    @ApiModelProperty(value = "产品数量")
    private Integer qty;

    /**
     * 单位
     */
    @TableField(value = "unit")
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * 折扣
     */
    @TableField(value = "rem")
    @ApiModelProperty(value = "折扣")
    private Integer rem;

    /**
     * 单价
     */
    @TableField(value = "unit_price")
    @ApiModelProperty(value = "单价")
    private String unitPrice;

    /**
     * 产品备注
     */
    @TableField(value = "product_note")
    @ApiModelProperty(value = "产品备注")
    private String productNote;

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

    public static final String COL_INVOICE_ID = "invoice_id";

    public static final String COL_PRODUCT_NAME = "product_name";

    public static final String COL_PRODUCT_NUM = "product_num";

    public static final String COL_TVA = "tva";

    public static final String COL_QTY = "qty";

    public static final String COL_UNIT = "unit";

    public static final String COL_REM = "rem";

    public static final String COL_UNIT_PRICE = "unit_price";

    public static final String COL_PRODUCT_NOTE = "product_note";

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