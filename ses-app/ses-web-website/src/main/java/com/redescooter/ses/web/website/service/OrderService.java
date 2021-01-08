package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.order.AddOrderEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;

/**
 * @Author jerry
 * @Date 2021/1/6 3:27 上午
 * @Description 订单服务
 **/
public interface OrderService {
    /**
     * 创建订单
     *
     * @param enter
     * @return
     */
    Boolean addOrder(AddOrderEnter enter);

    /**
     * 获取订单详情
     *
     * @param enter
     */
    OrderDetailsResult getOrderDetails(IdEnter enter);
}
