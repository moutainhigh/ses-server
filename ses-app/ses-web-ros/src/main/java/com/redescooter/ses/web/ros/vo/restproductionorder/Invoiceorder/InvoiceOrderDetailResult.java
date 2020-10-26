package com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.OperatingResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/10/26 11:39
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "发货单详情", description = "发货单详情")
@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceOrderDetailResult extends GeneralResult {

    @ApiModelProperty(value = "发货单Id")
    private Long id;

    @ApiModelProperty(value = "发货单单号")
    private String invoiceNo;

    @ApiModelProperty(value = "发货单状态")
    private Integer invoiceStatus;

    @ApiModelProperty(value = "交货时间")
    private Date deliveryDate;

    @ApiModelProperty(value = "发货人")
    private Long consignorUserId;

    @ApiModelProperty(value = "发货名称")
    private String consignorUserFistName;

    @ApiModelProperty(value = "发货人名称")
    private String consignorUserLastName;

    @ApiModelProperty(value = "发货人电话国家代码")
    private String consignorUserCountryCode;

    @ApiModelProperty(value = "发货人电话")
    private String consignorUserTelephone;

    @ApiModelProperty(value = "发货人邮箱")
    private String consignorUserEmail;

    @ApiModelProperty(value = "收获人")
    private Long consigneeUserId;

    @ApiModelProperty(value = "收获人名称")
    private String consigneeUserFistName;

    @ApiModelProperty(value = "收获人名称")
    private String consigneeUserLastName;

    @ApiModelProperty(value = "收获人国家编码")
    private String consigneeUserCountryCode;

    @ApiModelProperty(value = "收获人电话")
    private String consigneeUserTelephone;

    @ApiModelProperty(value = "收获人邮箱")
    private String consigneeUserEmail;

    @ApiModelProperty(value = "通知人")
    private Long notifyUserId;

    @ApiModelProperty(value = "通知人名称")
    private String notifyUserFistName;

    @ApiModelProperty(value = "通知人名称")
    private String notifyUserLastName;

    @ApiModelProperty(value = "通知人国家编码")
    private String notifyUserCountryCode;

    @ApiModelProperty(value = "通知人电话")
    private String notifyUserTelephone;

    @ApiModelProperty(value = "通知人邮箱")
    private String notifyUserEmail;

    @ApiModelProperty(value = "区域编码Id")
    private String zipCodeId;

    @ApiModelProperty(value = "区域编码")
    private String zipCodeName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "发货产品列表")
    private List<InvoiceOrderDetailProductResult> invoiceProductList;

    @ApiModelProperty(value = "关联订单")
    private List<InvoiceOrderDetailAssociatedOrderResult> associatedOrderList;

    @ApiModelProperty(value = "操作动态")
    private List<OperatingResult> orderOperatingList;
}
