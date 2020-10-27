package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:52
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "出库单详情", description = "出库单详情")
@Data
@EqualsAndHashCode(callSuper = true)
public class OutboundOrderDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "出库单号")
    private String outWhNo;

    @ApiModelProperty(value = "出库单状态")
    private Integer outWhStatus;

    @ApiModelProperty(value = "出库单类型")
    private Integer outWhType;

    @ApiModelProperty(value = "创建人")
    private Long createById;

    @ApiModelProperty(value = "创建人名称")
    private String createByFirstName;

    @ApiModelProperty(value = "创建人名称")
    private String createByLastName;

    @ApiModelProperty(value = "创建人国家代码")
    private Long createByCountryCodeId;

    @ApiModelProperty(value = "创建人国家代码")
    private String createByCountryCode;

    @ApiModelProperty(value = "创建人电话")
    private String createByTelephone;

    @ApiModelProperty(value = "创建人邮箱")
    private String createByMail;

    @ApiModelProperty(value = "产品列表")
    private List<OrderProductDetailResult> invoiceProductList;

    @ApiModelProperty(value = "关联订单")
    private List<AssociatedOrderResult> associatedOrderList;

    @ApiModelProperty(value = "订单操作日志")
    private List<OpTraceResult> orderOperatingList;
}
