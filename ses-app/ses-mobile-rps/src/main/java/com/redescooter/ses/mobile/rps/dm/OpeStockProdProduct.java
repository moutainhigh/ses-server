package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生产成品库
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_stock_prod_product")
public class OpeStockProdProduct implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField(value = "dr")
    private Integer dr;

    /**
     * 可用、破损
     */
    @TableField(value = "status")
    private String status;

    /**
     * 库存id
     */
    @TableField(value = "stock_id")
    private Long stockId;

    /**
     * 商品Id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 批次号
     */
    @TableField(value = "lot")
    private String lot;

    /**
     * 序列号
     */
    @TableField(value = "serial_number")
    private String serialNumber;

    /**
     * 部件号
     */
    @TableField(value = "product_number")
    private String productNumber;

    /**
     * 入库单Id
     */
    @TableField(value = "in_stock_bill_id")
    private Long inStockBillId;

    /**
     * 入库负责人Id
     */
    @TableField(value = "principal_id")
    private Long principalId;

    /**
     * 入库数量
     */
    @TableField(value = "in__wh_qty")
    private Integer inWhQty;

    /**
     * 入库时间
     */
    @TableField(value = "in_stock_time")
    private Date inStockTime;

    /**
     * 出库单Id
     */
    @TableField(value = "out_stock_bill_id")
    private Long outStockBillId;

    /**
     * 出库负责人
     */
    @TableField(value = "out_principal_id")
    private Long outPrincipalId;

    /**
     * 出库时间
     */
    @TableField(value = "out_stock_time")
    private Date outStockTime;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    private Integer revision;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_STOCK_ID = "stock_id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_LOT = "lot";

    public static final String COL_SERIAL_NUMBER = "serial_number";

    public static final String COL_PRODUCT_NUMBER = "product_number";

    public static final String COL_IN_STOCK_BILL_ID = "in_stock_bill_id";

    public static final String COL_PRINCIPAL_ID = "principal_id";

    public static final String COL_IN__WH_QTY = "in__wh_qty";

    public static final String COL_IN_STOCK_TIME = "in_stock_time";

    public static final String COL_OUT_STOCK_BILL_ID = "out_stock_bill_id";

    public static final String COL_OUT_PRINCIPAL_ID = "out_principal_id";

    public static final String COL_OUT_STOCK_TIME = "out_stock_time";

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

    public static OpeStockProdProductBuilder builder() {
        return new OpeStockProdProductBuilder();
    }
}