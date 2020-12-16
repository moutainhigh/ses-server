package com.redescooter.ses.api.common.service;

import com.redescooter.ses.api.common.vo.count.OrderCountEnter;
import com.redescooter.ses.api.common.vo.count.OrderCountResult;

import java.util.List;

/**
 * @ClassName:OrderCountService
 * @description: OrderCountService
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/16 17:06
 */
public interface OrderCountService {

    List<OrderCountResult> orderCount(OrderCountEnter enter);


}
