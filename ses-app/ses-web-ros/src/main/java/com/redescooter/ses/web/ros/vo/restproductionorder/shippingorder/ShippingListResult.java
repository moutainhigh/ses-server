package com.redescooter.ses.web.ros.vo.restproductionorder.shippingorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

/**
 *  @author: alex
 *  @Date: 2020/10/23 13:30
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShippingListResult extends GeneralResult {

    private Long id;

    private String invoiceNo;
}
