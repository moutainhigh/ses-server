package com.redescooter.ses.service.mobile.b.dm.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "com-redescooter-ses-service-mobile-b-dm-base-CorExpressDeliveryDetail")
@Data
@TableName(value = "cor_express_delivery_detail")
public class CorExpressDeliveryDetail implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    /**
     * 递送单主键
     */
    @TableField(value = "express_delivery_id")
    @ApiModelProperty(value = "递送单主键")
    private Long expressDeliveryId;

    /**
     * 订单主键
     */
    @TableField(value = "express_order_id")
    @ApiModelProperty(value = "订单主键")
    private Long expressOrderId;

    /**
     * delivery子订单状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "delivery子订单状态")
    private String status;

    /**
     * 包裹数
     */
    @TableField(value = "parcel_quantity")
    @ApiModelProperty(value = "包裹数")
    private Integer parcelQuantity;

    /**
     * 预计开始时间  快递业务没有 暂时做冗余字段
     */
    @TableField(value = "etd")
    @ApiModelProperty(value = "预计开始时间  快递业务没有 暂时做冗余字段")
    private Date etd;

    /**
     * 实际到达时间
     */
    @TableField(value = "ata")
    @ApiModelProperty(value = "实际到达时间")
    private Date ata;

    /**
     * 实际开始时间
     */
    @TableField(value = "atd")
    @ApiModelProperty(value = "实际开始时间")
    private Date atd;

    /**
     * 预计送达时间
     */
    @TableField(value = "eta")
    @ApiModelProperty(value = "预计送达时间")
    private Date eta;

    /**
     * 排序优先级，多个订单分配同一个配送单，所需先后顺序
     */
    @TableField(value = "priority_sort")
    @ApiModelProperty(value = "排序优先级，多个订单分配同一个配送单，所需先后顺序")
    private Integer prioritySort;

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

    public static final String COL_EXPRESS_DELIVERY_ID = "express_delivery_id";

    public static final String COL_EXPRESS_ORDER_ID = "express_order_id";

    public static final String COL_STATUS = "status";

    public static final String COL_PARCEL_QUANTITY = "parcel_quantity";

    public static final String COL_ETD = "etd";

    public static final String COL_ATA = "ata";

    public static final String COL_ATD = "atd";

    public static final String COL_ETA = "eta";

    public static final String COL_PRIORITY_SORT = "priority_sort";

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