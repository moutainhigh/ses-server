package com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowEnter;

/**
 *  @author: alex
 *  @Date: 2020/10/27 18:59
 *  @version：V ROS 1.8. *  @Description:
 */
public interface OrderStatusFlowService {

    GeneralResult save(OrderStatusFlowEnter enter);

}
