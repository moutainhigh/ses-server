package com.redescooter.ses.web.website.vo.order;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:41 上午
 * @Description 新增订单入参
 **/
@ApiModel(value = "Add order into parameter", description = "新增订单入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddOrderEnter extends GeneralEnter {

    /**
     * 订单类型，1销售，2租赁
     */
    @ApiModelProperty(value = "Order type, 1 sales, 2 lease")
    private int orderType;

    /**
     * 产品Id
     */
    @ApiModelProperty(value = "product_id",notes = "According to the vehicle model list, call the ID in the response result of vehicle product details")
    private Long productId;

    /**
     *
     */
    @ApiModelProperty(value = "productPartsId",notes = "Call to get the list of battery accessories according to the vehicle ID，142822372544512")
    private Long productPartsId;

    /**
     * 颜色主建
     */
    @ApiModelProperty(value = "colour_id")
    private Long colourId;

    /**
     * 提货方式
     */
    @ApiModelProperty(value = "delivery type: 1SELF_LIFT,-1DELIVER_HOME")
    private int deliveryType;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "payment_type_id",notes = "The ID in the response result of the interface calling the payment type")
    private Long paymentTypeId;

    /**
     * 需求车辆数
     */
    @ApiModelProperty(value = "Number of vehicles required",example = "1")
    private Integer scooterQuantity;

    /**
     * 备注
     */
    @ApiModelProperty(value = "remarks")
    private String remarks;

}
