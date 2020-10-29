package com.redescooter.ses.web.ros.vo.restproductionorder.consignorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/10/22 14:01
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "委托单详情", description = "委托单详情")
@Data
@EqualsAndHashCode(callSuper = true)
public class ConsignOrderDetailResult extends GeneralResult {

    @ApiModelProperty(value = "Id")
    private Long id;

    @ApiModelProperty(value = "发货单Id")
    private Long invoiceId;

    @ApiModelProperty(value = "委托单号")
    private String consignOrderNo;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    @ApiModelProperty(value = "货运方式")
    private Integer transportType;

    @ApiModelProperty(value = "发货人Id")
    private Long consignorUserId;

    @ApiModelProperty(value = "发货人名称")
    private String consignorUserFirstName;

    @ApiModelProperty(value = "发货人名称")
    private String consignorUserLastName;

    @ApiModelProperty(value = "发货人国家代码")
    private String consignorUserCountryCode;

    @ApiModelProperty(value = "发货人电话")
    private String consignorUserTelephone;

    @ApiModelProperty(value = "发货人邮箱")
    private String consignorUserMail;

    @ApiModelProperty(value = "收货人id")
    private Long consigneeUserId;

    @ApiModelProperty(value = "收货人名字")
    private String consigneeUserFirstName;

    @ApiModelProperty(value = "收货人名字")
    private String consigneeUserLastName;

    @ApiModelProperty(value = "收货人国家代码")
    private String consigneeUserCountryCode;

    @ApiModelProperty(value = "收货人电话")
    private String consigneeUserTelephone;

    @ApiModelProperty(value = "收货人邮箱")
    private String consigneeUserMail;

    @ApiModelProperty(value = "通知人Id")
    private Long notifyUserId;

    @ApiModelProperty(value = "通知人名字")
    private String notifyUserName;

    @ApiModelProperty(value = "通知人国家代码")
    private String notifyUserCountryCode;

    @ApiModelProperty(value = "通知人电话")
    private String notifyUserTelephone;

    @ApiModelProperty(value = "通知人邮箱")
    private String notifyUserMail;

    @ApiModelProperty(value = "区域代码")
    private String zipcodeName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "产品列表")
    private List<OrderProductDetailResult> productList;

    @ApiModelProperty(value = "关联订单")
    private List<AssociatedOrderResult> associatedOrderList;

    @ApiModelProperty(value = "操作动态")
    private List<OpTraceResult> orderOperatingList;
}
