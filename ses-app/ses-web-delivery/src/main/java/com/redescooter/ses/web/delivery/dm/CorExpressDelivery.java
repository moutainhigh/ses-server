package com.redescooter.ses.web.delivery.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@ApiModel(value = "com-redescooter-ses-web-delivery-dm-CorExpressDelivery")
@Data
@TableName(value = "cor_express_delivery")
public class CorExpressDelivery implements Serializable {
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
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 租户表
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户表")
    private Long tenantId;

    /**
     * 状态，WAITING 待配送 SHIPPING 正在配送 COMPLETED 已配送 REFUSED 失败
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态，WAITING 待配送 SHIPPING 正在配送 COMPLETED 已配送 REFUSED 失败")
    private String status;

    /**
     * 订单服务结果
     */
    @TableField(value = "result")
    @ApiModelProperty(value = "订单服务结果")
    private String result;

    /**
     * 司机ID
     */
    @TableField(value = "driver_id")
    @ApiModelProperty(value = "司机ID")
    private Long driverId;

    /**
     * 订单总数
     */
    @TableField(value = "order_sum")
    @ApiModelProperty(value = "订单总数")
    private Integer orderSum;

    /**
     * 订单完成数
     */
    @TableField(value = "order_complete_num")
    @ApiModelProperty(value = "订单完成数")
    private Integer orderCompleteNum;

    /**
     * 排单日期
     */
    @TableField(value = "delivery_date")
    @ApiModelProperty(value = "排单日期")
    private Date deliveryDate;

    /**
     * 开始配送时间
     */
    @TableField(value = "delivery_start_time")
    @ApiModelProperty(value = "开始配送时间")
    private Date deliveryStartTime;

    /**
     * 结束配送时间
     */
    @TableField(value = "delivery_end_time")
    @ApiModelProperty(value = "结束配送时间")
    private Date deliveryEndTime;

    /**
     * 行驶里程数
     */
    @TableField(value = "driven_mileage")
    @ApiModelProperty(value = "行驶里程数")
    private BigDecimal drivenMileage;

    /**
     * 行驶时长
     */
    @TableField(value = "driven_duration")
    @ApiModelProperty(value = "行驶时长")
    private Integer drivenDuration;

    /**
     * 二氧化碳排放量
     */
    @TableField(value = "co2")
    @ApiModelProperty(value = "二氧化碳排放量")
    private BigDecimal co2;

    /**
     * 节省金额
     */
    @TableField(value = "savings")
    @ApiModelProperty(value = "节省金额")
    private BigDecimal savings;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

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

    public static final String COL_STATUS = "status";

    public static final String COL_RESULT = "result";

    public static final String COL_DRIVER_ID = "driver_id";

    public static final String COL_ORDER_SUM = "order_sum";

    public static final String COL_ORDER_COMPLETE_NUM = "order_complete_num";

    public static final String COL_DELIVERY_DATE = "delivery_date";

    public static final String COL_DELIVERY_START_TIME = "delivery_start_time";

    public static final String COL_DELIVERY_END_TIME = "delivery_end_time";

    public static final String COL_DRIVEN_MILEAGE = "driven_mileage";

    public static final String COL_DRIVEN_DURATION = "driven_duration";

    public static final String COL_CO2 = "co2";

    public static final String COL_SAVINGS = "savings";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}