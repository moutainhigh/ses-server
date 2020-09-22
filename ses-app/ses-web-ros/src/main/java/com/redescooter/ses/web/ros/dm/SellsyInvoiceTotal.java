package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-SellsyInvoiceTotal")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sellsy_invoice_total")
public class SellsyInvoiceTotal implements Serializable {
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
    @TableLogic
    private Integer dr;

    /**
     * 客户名称
     */
    @TableField(value = "client_name")
    @ApiModelProperty(value = "客户名称")
    private String clientName;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 发票单号
     */
    @TableField(value = "invoice_num")
    @ApiModelProperty(value = "发票单号")
    private String invoiceNum;

    /**
     * 开具发票的时间
     */
    @TableField(value = "invoice_time")
    @ApiModelProperty(value = "开具发票的时间")
    private Date invoiceTime;

    /**
     * 不含税价格
     */
    @TableField(value = "ht")
    @ApiModelProperty(value = "不含税价格")
    private String ht;

    /**
     * 价税合计
     */
    @TableField(value = "ttc")
    @ApiModelProperty(value = "价税合计")
    private String ttc;

    /**
     * tva
     */
    @TableField(value = "tva")
    @ApiModelProperty(value = "tva")
    private String tva;

    /**
     * 支付状态
     */
    @TableField(value = "pay_status")
    @ApiModelProperty(value = "支付状态")
    private String payStatus;

    /**
     * 支付类型
     */
    @TableField(value = "pay_type")
    @ApiModelProperty(value = "支付类型")
    private String payType;

    /**
     * 支付时间
     */
    @TableField(value = "pay_time")
    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    /**
     * 电动摩托车及其配件
     */
    @TableField(value = "product_name")
    @ApiModelProperty(value = "电动摩托车及其配件")
    private String productName;

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
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_CLIENT_NAME = "client_name";

    public static final String COL_STATUS = "status";

    public static final String COL_INVOICE_NUM = "invoice_num";

    public static final String COL_INVOICE_TIME = "invoice_time";

    public static final String COL_HT = "ht";

    public static final String COL_TTC = "ttc";

    public static final String COL_TVA = "tva";

    public static final String COL_PAY_STATUS = "pay_status";

    public static final String COL_PAY_TYPE = "pay_type";

    public static final String COL_PAY_TIME = "pay_time";

    public static final String COL_PRODUCT_NAME = "product_name";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF6 = "def6";
}