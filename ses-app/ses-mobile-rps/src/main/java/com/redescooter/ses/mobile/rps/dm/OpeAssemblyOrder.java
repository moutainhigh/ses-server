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
 * 组装单
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeAssemblyOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeAssemblyOrder implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_TENANT_ID = "tenant_id";
    public static final String COL_STATUS = "status";
    public static final String COL_ASSEMBLY_NUMBER = "assembly_number";
    public static final String COL_TOTAL_QTY = "total_qty";
    public static final String COL_TOTAL_PRICE = "total_price";
    public static final String COL_PROCESSING_FEE_RATIO = "processing_fee_ratio";
    public static final String COL_PROCESSING_FEE = "processing_fee";
    public static final String COL_PAYMENT_TYPE = "payment_type";
    public static final String COL_PRODUCT_PRICE = "product_price";
    public static final String COL_FACTORY_ID = "factory_id";
    public static final String COL_FACTORY_ANNEX = "factory_annex";
    public static final String COL_CONSIGNEE_ID = "consignee_id";
    public static final String COL_WAIT_ASSEMBLY_TOTAL = "wait_assembly_total";
    public static final String COL_IN_WAIT_WH_TOTAL = "in_wait_wh_total";
    public static final String COL_LAVE_WAIT_QC_TOTAL = "lave_wait_qc_total";
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
     * 逻辑删除标识
     */
    @ApiModelProperty(value = "逻辑删除标识")
    private Integer dr;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 组装单订单号
     */
    @ApiModelProperty(value = "组装单订单号")
    private String assemblyNumber;

    /**
     * 产品数量之和
     */
    @ApiModelProperty(value = "产品数量之和")
    private Integer totalQty;

    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalPrice;

    /**
     * 加工费比例
     */
    @ApiModelProperty(value = "加工费比例")
    private BigDecimal processingFeeRatio;

    /**
     * 加工费
     */
    @ApiModelProperty(value = "加工费")
    private BigDecimal processingFee;

    /**
     * 付款类型
     */
    @ApiModelProperty(value = "付款类型")
    private String paymentType;

    /**
     * 产品生产价格
     */
    @ApiModelProperty(value = "产品生产价格")
    private BigDecimal productPrice;

    /**
     * 代工厂id
     */
    @ApiModelProperty(value = "代工厂id")
    private Long factoryId;

    /**
     * 工厂附件
     */
    @ApiModelProperty(value = "工厂附件")
    private String factoryAnnex;

    /**
     * 收货人姓氏
     */
    @ApiModelProperty(value = "收货人姓氏")
    private Long consigneeId;

    /**
     * 待组装总数量
     */
    @ApiModelProperty(value = "待组装总数量")
    private Integer waitAssemblyTotal;

    /**
     * 待入库总数
     */
    @ApiModelProperty(value = "待入库总数")
    private String inWaitWhTotal;

    /**
     * 待质检总数
     */
    @ApiModelProperty(value = "待质检总数")
    private Integer laveWaitQcTotal;

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

    public static OpeAssemblyOrderBuilder builder() {
        return new OpeAssemblyOrderBuilder();
    }
}