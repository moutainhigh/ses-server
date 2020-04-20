package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 采购单条目
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpePurchasB")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_purchas_b")
public class OpePurchasB implements Serializable {
    public static final String COL_ID_CLASS = "id_class";
    /**
     * 主键
     */
    @TableField(value = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除表示
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除表示")
    private Integer dr;

    /**
     * 租户Id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    /**
     * 租户Id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "租户Id")
    private Long userId;

    /**
     * 采购订单Id
     */
    @TableField(value = "purchas_id")
    @ApiModelProperty(value = "采购订单Id")
    private Long purchasId;

    /**
     * 部品Id
     */
    @TableField(value = "part_id")
    @ApiModelProperty(value = "部品Id")
    private Long partId;

    /**
     * 供应商Id
     */
    @TableField(value = "supplier_id")
    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;

    /**
     * 供应商附件
     */
    @TableField(value = "supplier_annex")
    @ApiModelProperty(value = "供应商附件")
    private String supplierAnnex;

    /**
     * qc质检状态
     */
    @TableField(value = "qc_status")
    @ApiModelProperty(value = "qc质检状态")
    private String qcStatus;

    /**
     * 单价
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    /**
     * 总数量
     */
    @TableField(value = "total_count")
    @ApiModelProperty(value = "总数量")
    private Integer totalCount;

    /**
     * 总价格
     */
    @TableField(value = "total_price")
    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice;

    /**
     * 待质检数
     */
    @TableField(value = "lave_wait_qc_qty")
    @ApiModelProperty(value = "待质检数")
    private Integer laveWaitQcQty;

    /**
     * 待入库数量
     */
    @TableField(value = "in_wait_wh_qty")
    @ApiModelProperty(value = "待入库数量")
    private Integer inWaitWhQty;

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

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_PURCHAS_ID = "purchas_id";

    public static final String COL_PART_ID = "part_id";

    public static final String COL_SUPPLIER_ID = "supplier_id";

    public static final String COL_SUPPLIER_ANNEX = "supplier_annex";

    public static final String COL_QC_STATUS = "qc_status";

    public static final String COL_PRICE = "price";

    public static final String COL_TOTAL_COUNT = "total_count";

    public static final String COL_TOTAL_PRICE = "total_price";

    public static final String COL_LAVE_WAIT_QC_QTY = "lave_wait_qc_qty";

    public static final String COL_IN_WAIT_WH_QTY = "in_wait_wh_qty";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

    public static OpePurchasBBuilder builder() {
        return new OpePurchasBBuilder();
    }
}