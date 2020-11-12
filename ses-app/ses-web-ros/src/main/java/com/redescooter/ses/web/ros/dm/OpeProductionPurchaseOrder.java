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

/**
 * 生产采购单表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeProductionPurchaseOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_production_purchase_order")
public class OpeProductionPurchaseOrder {
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
    private Integer dr;

    /**
     * 采购单号
     */
    @TableField(value = "purchase_no")
    @ApiModelProperty(value = "采购单号")
    private String purchaseNo;

    /**
     * 采购状态，1：草稿，10：采购中，20：待入库，30：部分入库，40：已入库，50：已完成，60：已取消
     */
    @TableField(value = "purchase_status")
    @ApiModelProperty(value = "采购状态，1：草稿，10：采购中，20：待入库，30：部分入库，40：已入库，50：已完成，60：已取消")
    private Integer purchaseStatus;

    /**
     * 采购数量
     */
    @TableField(value = "purchase_qty")
    @ApiModelProperty(value = "采购数量")
    private Integer purchaseQty;

    /**
     * 采购总金额
     */
    @TableField(value = "purchase_amount")
    @ApiModelProperty(value = "采购总金额")
    private BigDecimal purchaseAmount;

    /**
     * 工厂id
     */
    @TableField(value = "factory_id")
    @ApiModelProperty(value = "工厂id")
    private Long factoryId;

    /**
     * 负责人id
     */
    @TableField(value = "factory_principal_id")
    @ApiModelProperty(value = "负责人id")
    private Long factoryPrincipalId;

    /**
     * 负责人名称
     */
    @TableField(value = "factory_principal_name")
    @ApiModelProperty(value = "负责人名称")
    private String factoryPrincipalName;

    /**
     * 负责人国家编码如+86
     */
    @TableField(value = "principal_country_code")
    @ApiModelProperty(value = "负责人国家编码如+86")
    private String principalCountryCode;

    /**
     * 负责人电话
     */
    @TableField(value = "principal_telephone")
    @ApiModelProperty(value = "负责人电话")
    private String principalTelephone;

    /**
     * 交货日期
     */
    @TableField(value = "delivery_date")
    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    /**
     * 采购对接人id
     */
    @TableField(value = "docking_user")
    @ApiModelProperty(value = "采购对接人id")
    private Long dockingUser;

    /**
     * 采购对接人名称
     */
    @TableField(value = "docking_user_name")
    @ApiModelProperty(value = "采购对接人名称")
    private String dockingUserName;

    /**
     * 采购对接人国家编码如+86
     */
    @TableField(value = "docking_country_code")
    @ApiModelProperty(value = "采购对接人国家编码如+86")
    private String dockingCountryCode;

    /**
     * 采购对接人电话
     */
    @TableField(value = "docking_user_telephone")
    @ApiModelProperty(value = "采购对接人电话")
    private String dockingUserTelephone;

    /**
     * 收货人id
     */
    @TableField(value = "consignee_user")
    @ApiModelProperty(value = "收货人id")
    private Long consigneeUser;

    /**
     * 收货人名称
     */
    @TableField(value = "consignee_user_name")
    @ApiModelProperty(value = "收货人名称")
    private String consigneeUserName;

    /**
     * 收货人国家编码如+86
     */
    @TableField(value = "consignee_country_code")
    @ApiModelProperty(value = "收货人国家编码如+86")
    private String consigneeCountryCode;

    /**
     * 收货人电话
     */
    @TableField(value = "consignee_user_telephone")
    @ApiModelProperty(value = "收货人电话")
    private String consigneeUserTelephone;

    /**
     * 收获地址
     */
    @TableField(value = "consignee_address")
    @ApiModelProperty(value = "收获地址")
    private String consigneeAddress;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 支付方式，1：月结，2：预付款，3：全款
     */
    @TableField(value = "payment_type")
    @ApiModelProperty(value = "支付方式，1：月结，2：预付款，3：全款")
    private Integer paymentType;

    /**
     * 预计付款时间
     */
    @TableField(value = "planned_payment_time")
    @ApiModelProperty(value = "预计付款时间")
    private Date plannedPaymentTime;

    /**
     * 付款周期
     */
    @TableField(value = "payment_day")
    @ApiModelProperty(value = "付款周期")
    private Integer paymentDay;

    /**
     * 实际付款时间
     */
    @TableField(value = "payment_time")
    @ApiModelProperty(value = "实际付款时间")
    private Date paymentTime;

    /**
     * 采购合同
     */
    @TableField(value = "purchase_contract")
    @ApiModelProperty(value = "采购合同")
    private String purchaseContract;

    /**
     * 预付款比例(小数点最多一位)
     */
    @TableField(value = "pre_pay_ratio")
    @ApiModelProperty(value = "预付款比例(小数点最多一位)")
    private BigDecimal prePayRatio;

    /**
     * 支付金额
     */
    @TableField(value = "pay_amount")
    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

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

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_PURCHASE_NO = "purchase_no";

    public static final String COL_PURCHASE_STATUS = "purchase_status";

    public static final String COL_PURCHASE_QTY = "purchase_qty";

    public static final String COL_PURCHASE_AMOUNT = "purchase_amount";

    public static final String COL_FACTORY_ID = "factory_id";

    public static final String COL_FACTORY_PRINCIPAL_ID = "factory_principal_id";

    public static final String COL_FACTORY_PRINCIPAL_NAME = "factory_principal_name";

    public static final String COL_PRINCIPAL_COUNTRY_CODE = "principal_country_code";

    public static final String COL_PRINCIPAL_TELEPHONE = "principal_telephone";

    public static final String COL_DELIVERY_DATE = "delivery_date";

    public static final String COL_DOCKING_USER = "docking_user";

    public static final String COL_DOCKING_USER_NAME = "docking_user_name";

    public static final String COL_DOCKING_COUNTRY_CODE = "docking_country_code";

    public static final String COL_DOCKING_USER_TELEPHONE = "docking_user_telephone";

    public static final String COL_CONSIGNEE_USER = "consignee_user";

    public static final String COL_CONSIGNEE_USER_NAME = "consignee_user_name";

    public static final String COL_CONSIGNEE_COUNTRY_CODE = "consignee_country_code";

    public static final String COL_CONSIGNEE_USER_TELEPHONE = "consignee_user_telephone";

    public static final String COL_CONSIGNEE_ADDRESS = "consignee_address";

    public static final String COL_REMARK = "remark";

    public static final String COL_PAYMENT_TYPE = "payment_type";

    public static final String COL_PLANNED_PAYMENT_TIME = "planned_payment_time";

    public static final String COL_PAYMENT_DAY = "payment_day";

    public static final String COL_PAYMENT_TIME = "payment_time";

    public static final String COL_PURCHASE_CONTRACT = "purchase_contract";

    public static final String COL_PRE_PAY_RATIO = "pre_pay_ratio";

    public static final String COL_PAY_AMOUNT = "pay_amount";

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