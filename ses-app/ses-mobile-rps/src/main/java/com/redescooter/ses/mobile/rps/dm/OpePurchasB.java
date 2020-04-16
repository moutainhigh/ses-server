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
 * 采购单条目
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpePurchasB")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpePurchasB implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_TENANT_ID = "tenant_id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_PURCHAS_ID = "purchas_id";
    public static final String COL_PART_ID = "part_id";
    public static final String COL_ID_CLASS = "id_class";
    public static final String COL_SUPPLIER_ID = "supplier_id";
    public static final String COL_SUPPLIER_ANNEX = "supplier_annex";
    public static final String COL_QC_STATUS = "qc_status";
    public static final String COL_PRICE = "price";
    public static final String COL_TOTAL_COUNT = "total_count";
    public static final String COL_TOTAL_PRICE = "total_price";
    public static final String COL_LAVE_WAIT_QC_QTY = "lave_wait_qc_qty";
    public static final String COL_IN_WAIT_WH_QTY = "in_wait_wh_qty";
    public static final String COL_TOTAL_INVENTORY = "total_inventory";
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
     * 逻辑删除表示
     */
    @ApiModelProperty(value = "逻辑删除表示")
    private Integer dr;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id")
    private Long userId;

    /**
     * 采购订单Id
     */
    @ApiModelProperty(value = "采购订单Id")
    private Long purchasId;

    /**
     * 部品Id
     */
    @ApiModelProperty(value = "部品Id")
    private Long partId;

    /**
     * 质检方式
     */
    @ApiModelProperty(value = "质检方式")
    private String idClass;

    /**
     * 供应商Id
     */
    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;

    /**
     * 供应商附件
     */
    @ApiModelProperty(value = "供应商附件")
    private String supplierAnnex;

    /**
     * qc质检状态
     */
    @ApiModelProperty(value = "qc质检状态")
    private String qcStatus;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量")
    private Integer totalCount;

    /**
     * 总价格
     */
    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice;

    /**
     * 待质检数
     */
    @ApiModelProperty(value = "待质检数")
    private Integer laveWaitQcQty;

    /**
     * 待入库数量
     */
    @ApiModelProperty(value = "待入库数量")
    private Integer inWaitWhQty;

    /**
     * 已入库总数
     */
    @ApiModelProperty(value = "已入库总数")
    private Integer totalInventory;

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

    public static OpePurchasBBuilder builder() {
        return new OpePurchasBBuilder();
    }
}