package com.redescooter.ses.web.ros.vo.sales;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: ses-server
 * @description: 销售订单详情
 * @author: Jerry
 * @created: 2020/09/29 22:37
 */
@ApiModel(value = "销售单详情", description = "销售单详情")
@Data
public class SalesOrderDetailsResult extends GeneralResult {

    @ApiModelProperty(value = "销售订单主键")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态")
    private String status;

    @ApiModelProperty(value = "订单支付状态")
    private String payStatus;

    @ApiModelProperty(value = "客户全名")
    private String customerFullName;

    @ApiModelProperty(value = "客户邮箱")
    private String email;

    @ApiModelProperty(value = "车辆小类")
    private String productModel;

    @ApiModelProperty(value = "车辆类型英文名")
    private String enName;

    @ApiModelProperty(value = "车辆颜色")
    private String color;

    @ApiModelProperty(value = "车辆颜色名称")
    private String colorName;

    @ApiModelProperty(value = "车辆颜色值")
    private String colorValue;

    @ApiModelProperty(value = "车辆数量")
    private int qty;

    @ApiModelProperty(value = "电池数量")
    private int batteryQty;

    @ApiModelProperty(value = "订单总金额")
    private Double amount;

    @ApiModelProperty(value = "订单预付款")
    private Double paid;

    @ApiModelProperty(value = "订单余额")
    private Double balance;

    @ApiModelProperty(value = "订单创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "优惠金额")
    private Double discount;

    @ApiModelProperty(value = "手机区号")
    private String countryCode;

    @ApiModelProperty(value = "客户手机号")
    private String telephone;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "邮政编码")
    private String postcode;

    @ApiModelProperty(value = "客户地址")
    private String address;

    @ApiModelProperty(value = "倒计时")
    private Long ttl = 180L;

    @ApiModelProperty("预付定金")
    private Double prepaidDeposit;

}
