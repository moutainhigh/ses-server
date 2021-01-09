package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.service.OrderService;
import com.redescooter.ses.web.website.vo.order.AddOrderEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author jerry
 * @Date 2021/1/9 7:19 下午
 * @Description 订单服务
 **/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    /**
     * 创建订单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addOrder(AddOrderEnter enter) {
        return null;
    }

    /**
     * 修改订单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult modifyOrder(AddOrderEnter enter) {
        return null;
    }

    /**
     * 获取订单详情
     *
     * @param enter
     */
    @Override
    public OrderDetailsResult getOrderDetails(IdEnter enter) {
        return null;
    }
}
