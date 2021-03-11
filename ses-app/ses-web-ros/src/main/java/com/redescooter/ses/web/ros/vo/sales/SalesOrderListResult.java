package com.redescooter.ses.web.ros.vo.sales;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: ses-server
 * @description: 销售列表出参
 * @author: Jerry
 * @created: 2020/09/29 22:33
 */
@ApiModel(value = "销售订单列表结果集", description = "销售订单列表结果集")
@Data
public class SalesOrderListResult extends GeneralResult {

    @ApiModelProperty(value = "销售订单主键")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态")
    private String status;

    @ApiModelProperty(value = "订单支付状态")
    private String payStatus;

    @ApiModelProperty(value = "客户全名")
    private String customerFullName = "--";

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

    @ApiModelProperty(value = "订单总金额")
    private Double amount;

    @ApiModelProperty(value = "订单预付款")
    private Double paid;

    @ApiModelProperty(value = "优惠抵扣金额")
    private Double amountDiscount;

    @ApiModelProperty(value = "订单余额")
    private Double balance;

    @ApiModelProperty(value = "标签标记")
    private String labelFlag;

    @ApiModelProperty(value = "提示标记")
    private Boolean warnFlag;

    @ApiModelProperty(value = "订单创建时间")
    private Date createdTime;

}
