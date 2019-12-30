package com.redescooter.ses.api.common.vo.delivery;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:BaseDeliveryEnter
 * @description: BaseDeliveryEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 17:14
 */
@Data
public class BaseDeliveryEnter extends GeneralEnter {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 交付人,即司机账号的USER_ID字段的值
     */
    @ApiModelProperty(value = "交付人,即司机账号的USER_ID字段的值")
    private Long delivererId;

    /**
     * scooterId
     */
    @ApiModelProperty(value = "scooterId")
    private Long scooterId;

    /**
     * 收件人
     */
    @ApiModelProperty(value = "收件人")
    private String recipient;

    /**
     * 收件人邮箱
     */
    @ApiModelProperty(value = "收件人邮箱")
    private String recipientEmail;

    /**
     * 收件人电话
     */
    @ApiModelProperty(value = "收件人电话")
    private String recipientTel;

    /**
     * 收件人地址
     */
    @ApiModelProperty(value = "收件人地址")
    private String recipientAddress;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    /**
     * GeoHash
     */
    @ApiModelProperty(value = "GeoHash")
    private String geohash;

    /**
     * 门牌信息
     */
    @ApiModelProperty(value = "门牌信息")
    private String houseInfo;

    /**
     * 包裹数量
     */
    @ApiModelProperty(value = "包裹数量")
    private Integer parcelQuantity;

    /**
     * 商品清单
     */
    @ApiModelProperty(value = "商品清单")
    private String goodsInventory;

    /**
     * 订单服务结果  按时、延迟、取消  ONTIME、DELAY、CANCEl
     */
    @ApiModelProperty(value = "订单服务结果  按时、延迟、取消  ONTIME、DELAY、CANCEl")
    private String result;

    /**
     * 订单状态:PENDING待配送，  DELIVERING正在配送，REJECTED拒绝，TIMEOUT配送超时，COMPLETE已送达，CANCEL取消失败；PC订单状态显示:待配送Pending,配送中In  progress,拒单Declined,配送超时Overdue,超时完成Delayed,已送达Delivered,取消订单Cancelled
     */
    @ApiModelProperty(value = "订单状态:PENDING待配送，  DELIVERING正在配送，REJECTED拒绝，TIMEOUT配送超时，COMPLETE已送达，CANCEL取消失败；PC订单状态显示:待配送Pending,配送中In  progress,拒单Declined,配送超时Overdue,超时完成Delayed,已送达Delivered," +
            "取消订单Cancelled")
    private String status;

    /**
     * 安排配送时间
     */
    @ApiModelProperty(value = "安排配送时间")
    private String plannedTime;

    /**
     * 超时预警值，单位分钟
     */
    @ApiModelProperty(value = "超时预警值，单位分钟")
    private String timeoutExpectde;

    /**
     * 超时预警时间
     */
    @ApiModelProperty(value = "超时预警时间")
    private Date timeOut;

    /**
     * 预计开始时间
     */
    @ApiModelProperty(value = "预计开始时间")
    private Date etd;

    /**
     * 实际开始时间
     */
    @ApiModelProperty(value = "实际开始时间")
    private Date atd;

    /**
     * 预计送达时间
     */
    @ApiModelProperty(value = "预计送达时间")
    private Date eta;

    /**
     * 实际送达时间
     */
    @ApiModelProperty(value = "实际送达时间")
    private Date ata;

    /**
     * 骑行里程
     */
    @ApiModelProperty(value = "骑行里程")
    private BigDecimal drivenMileage;

    /**
     * 骑行时长
     */
    @ApiModelProperty(value = "骑行时长")
    private Integer drivenDuration;

    /**
     * 二氧化碳排放量
     */
    @ApiModelProperty(value = "二氧化碳排放量")
    private BigDecimal co2;

    /**
     * 节省金额
     */
    @ApiModelProperty(value = "节省金额")
    private BigDecimal savings;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

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
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;
}
