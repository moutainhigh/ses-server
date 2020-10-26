package com.redescooter.ses.web.ros.vo.restproductionorder.shippingorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/23 13:30
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "采购单列表", description = "采购单列表")
@Data
@EqualsAndHashCode(callSuper = true)
public class ShippingListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "采购单单号")
    private String invoiceNo;

    @ApiModelProperty(value = "采购单状态")
    private String invoiceStatus;

    @ApiModelProperty(value = "采购单类型")
    private Integer invoiceType;

    @ApiModelProperty(value = "采购数量")
    private Integer invoiceQty;

    @ApiModelProperty(value = "关联单据单号")
    private Long purchaseId;

    @ApiModelProperty(value = "关联单据单号")
    private String purchaseNo;

    @ApiModelProperty(value = "到期交付时间")
    private Date deliveryDate;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal invoiceAmount;

    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;

    @ApiModelProperty(value = "供应商名字")
    private String supplierName;

    @ApiModelProperty(value = "收货人Id")
    private Long receiverId;

    @ApiModelProperty(value = "收货人名称")
    private String receiverFirstName;

    @ApiModelProperty(value = "收货人名称")
    private String receiverLastName;

    @ApiModelProperty(value = "创建人Id")
    private Long createById;

    @ApiModelProperty(value = "创建人名称")
    private String createByFirstName;

    @ApiModelProperty(value = "创建人名称")
    private String createByLastName;

    @ApiModelProperty(value = "创建时间")
    private Date createByDate;
}
