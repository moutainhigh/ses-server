package com.redescooter.ses.web.ros.vo.restproductionorder.shippingorder;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.Data;

import io.swagger.annotations.*;

/**
 *  @author: alex
 *  @Date: 2020/10/23 17:30
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "发货单列表入参", description = "发货单列表入参")
@Data
public class ShippingListEnter extends PageEnter {
    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "发货单状态")
    private String invoiceStatus;
}
