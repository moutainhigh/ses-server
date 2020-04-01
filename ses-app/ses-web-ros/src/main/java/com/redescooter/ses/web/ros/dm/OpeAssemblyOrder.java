package com.redescooter.ses.web.ros.dm;

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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeAssemblyOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_assembly_order")
public class OpeAssemblyOrder implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识")
    private Integer dr;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 组装单订单号
     */
    @TableField(value = "assembly_number")
    @ApiModelProperty(value = "组装单订单号")
    private String assemblyNumber;

    /**
     * 产品数量之和
     */
    @TableField(value = "total_qty")
    @ApiModelProperty(value = "产品数量之和")
    private Integer totalQty;

    /**
     * 总金额
     */
    @TableField(value = "total_price")
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalPrice;

    /**
     * 加工费
     */
    @TableField(value = "processing_fee")
    @ApiModelProperty(value = "加工费")
    private BigDecimal processingFee;

    /**
     * 付款类型
     */
    @TableField(value = "payment_type")
    @ApiModelProperty(value = "付款类型")
    private Integer paymentType;

    /**
     * 付款比例 过时
     */
    @TableField(value = "payment_ratio")
    @ApiModelProperty(value = "付款比例 过时")
    private String paymentRatio;

    /**
     * 代工厂id
     */
    @TableField(value = "factory_id")
    @ApiModelProperty(value = "代工厂id")
    private Long factoryId;

    /**
     * 收货人姓氏
     */
    @TableField(value = "consignee_id")
    @ApiModelProperty(value = "收货人姓氏")
    private Integer consigneeId;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_STATUS = "status";

    public static final String COL_ASSEMBLY_NUMBER = "assembly_number";

    public static final String COL_TOTAL_QTY = "total_qty";

    public static final String COL_TOTAL_PRICE = "total_price";

    public static final String COL_PROCESSING_FEE = "processing_fee";

    public static final String COL_PAYMENT_TYPE = "payment_type";

    public static final String COL_PAYMENT_RATIO = "payment_ratio";

    public static final String COL_FACTORY_ID = "factory_id";

    public static final String COL_CONSIGNEE_ID = "consignee_id";

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

    public static OpeAssemblyOrderBuilder builder() {
        return new OpeAssemblyOrderBuilder();
    }
}