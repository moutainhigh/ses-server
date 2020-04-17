package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpePurchas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_purchas")
public class OpePurchas {
    /**
     * 主键 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键 主键")
    private Long id;

    /**
     * 逻辑删除标识 逻辑删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 逻辑删除标识")
    private Integer dr;

    /**
     * 租户Id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "租户Id")
    private Long userId;

    /**
     * 租户Id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    /**
     * 收货人Id
     */
    @TableField(value = "consignee_id")
    @ApiModelProperty(value = "收货人Id")
    private Long consigneeId;

    /**
     * 采购单编号 采购单标号
     */
    @TableField(value = "contract_no")
    @ApiModelProperty(value = "采购单编号 采购单标号")
    private String contractNo;

    /**
     * 状态 采购单状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态 采购单状态")
    private String status;

    /**
     * 付款类型
     */
    @TableField(value = "payment_type")
    @ApiModelProperty(value = "付款类型")
    private String paymentType;

    /**
     * 工厂主键 代工厂Id
     */
    @TableField(value = "factory_id")
    @ApiModelProperty(value = "工厂主键 代工厂Id")
    private Long factoryId;

    /**
     * 工厂组装附件
     */
    @TableField(value = "factory_annex")
    @ApiModelProperty(value = "工厂组装附件")
    private String factoryAnnex;

    /**
     * 总金额 总金额
     */
    @TableField(value = "total_price")
    @ApiModelProperty(value = "总金额 总金额")
    private BigDecimal totalPrice;

    /**
     * 总数量 总数量
     */
    @TableField(value = "total_qty")
    @ApiModelProperty(value = "总数量 总数量")
    private Integer totalQty;

    /**
     * 待质检总数
     */
    @TableField(value = "lave_wait_qc_total")
    @ApiModelProperty(value = "待质检总数")
    private Integer laveWaitQcTotal;

    /**
     * 待入库总数
     */
    @TableField(value = "in_wait_wh_total")
    @ApiModelProperty(value = "待入库总数")
    private Integer inWaitWhTotal;

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

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_CONSIGNEE_ID = "consignee_id";

    public static final String COL_CONTRACT_NO = "contract_no";

    public static final String COL_STATUS = "status";

    public static final String COL_PAYMENT_TYPE = "payment_type";

    public static final String COL_FACTORY_ID = "factory_id";

    public static final String COL_FACTORY_ANNEX = "factory_annex";

    public static final String COL_TOTAL_PRICE = "total_price";

    public static final String COL_TOTAL_QTY = "total_qty";

    public static final String COL_LAVE_WAIT_QC_TOTAL = "lave_wait_qc_total";

    public static final String COL_IN_WAIT_WH_TOTAL = "in_wait_wh_total";

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

    public static OpePurchasBuilder builder() {
        return new OpePurchasBuilder();
    }
}