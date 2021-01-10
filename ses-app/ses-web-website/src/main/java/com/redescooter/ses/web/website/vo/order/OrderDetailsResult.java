package com.redescooter.ses.web.website.vo.order;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
     * 客户id
     */
    @ApiModelProperty(value = "customer_id")
    private Long customerId;

    /**
     * 销售员id
     */
    @ApiModelProperty(value = "sales_id")
    private Long salesId;

    /**
     * 状态,1进行中，2支付中，3取消退款，4已完成，5关闭
     */
    @ApiModelProperty(value = "Status: 1 new, 2 deposit paid successfully, 3 balance to be paid, 4 refund cancelled, 5 completed, 6 closed")
    private String status;

    /**
     * 订单类型，1销售，2租赁
     */
    @ApiModelProperty(value = "Order type, 1 sales, 2 lease")
    private String orderType;

    /**
     * 产品Id
     */
    @ApiModelProperty(value = "product_id ")
    private Long productId;

    /**
     * 颜色主建
     */
    @ApiModelProperty(value = "colour_id")
    private Long colourId;

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
    @ApiModelProperty(value = "payment_type_id")
    private Long paymentTypeId;

    /**
     * 需求车辆数
     */
    @ApiModelProperty(value = "Number of vehicles required")
    private Integer scooterQuantity;

    /**
     * 预计交货时间
     */
    @ApiModelProperty(value = "Estimated delivery time")
    private Date etdDeliveryTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "remarks")
    private String remarks;


}
