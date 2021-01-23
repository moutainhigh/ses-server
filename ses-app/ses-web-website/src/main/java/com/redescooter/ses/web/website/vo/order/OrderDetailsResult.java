package com.redescooter.ses.web.website.vo.order;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 3:42 上午
 * @Description 订单结果集出参
 **/

@ApiModel(value = "订单结果集出参", description = "订单结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDetailsResult extends GeneralResult {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "order ID")
    private Long orderId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "Order number")
    private String orderNo;

    /**
     * 状态,1进行中，2支付中，3取消退款，4已完成，5关闭
     */
    @ApiModelProperty(value = "Start: 1 NEWS, 2 TO_BE_PAID, 3 in_progress, 4 CANCELLED, 5 COMPLETED, 6 CLOSED")
    private String status;

    /**
     * 支付状态
     */
    @ApiModelProperty(value = "pay_status: 1 UN_PAID, 2 PARTIALLY_PAID, 3 PAID")
    private String payStatus;

    /**
     * 订单类型，1销售，2租赁
     */
    @ApiModelProperty(value = "Order type, 1 sales, 2 lease")
    private String orderType;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "productName")
    private String productName;

    /**
     * 运费
     */
    @ApiModelProperty(value = "freight")
    private String freight;

    /**
     * 产品颜色
     */
    @ApiModelProperty(value = "product_colour_name ")
    private String productColour;

    /**
     * 提货方式
     */
    @ApiModelProperty(value = "delivery_type")
    private String deliveryType;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "payment Type Name")
    private String paymentName;

    /**
     * 需求车辆数
     */
    @ApiModelProperty(value = "Number of vehicles required")
    private Integer scooterQuantity;

    /**
     * 购买电池数量
     */
    @ApiModelProperty(value = "battery Qty")
    private Integer batteryQty;

    /**
     * 预计交货时间
     */
    @ApiModelProperty(value = "Estimated delivery time")
    private Date etdDeliveryTime;

    /**
     * 整车价格
     */
    @ApiModelProperty(value = "Scooter product Price")
    private BigDecimal productPrice;

    /**
     * 单据总价
     */
    @ApiModelProperty(value = "total_price")
    private BigDecimal totalPrice;

    /**
     * 已付金额
     */
    @ApiModelProperty(value = "amount_paid")
    private BigDecimal amountPaid;

    /**
     * 子订单
     */
    private List<orderBlist> orderBlist = new ArrayList<>();
}

@ApiModel(value = "子订单结果集出参", description = "子订单结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
class orderBlist extends GeneralResult {

    /**
     * 子订单主建
     */
    @ApiModelProperty(value = "order Suborder id")
    private Long orderBId;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "English name")
    private String enName;

    /**
     * 销售价格
     */
    @ApiModelProperty(value = "parts Price")
    private BigDecimal partsPrice;

    /**
     * 配件数量
     */
    @ApiModelProperty(value = "parts_qty")
    private Integer partsQty;
}
