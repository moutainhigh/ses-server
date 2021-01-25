package com.redescooter.ses.web.website.dao;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;

/**
 * @Author jerry
 * @Date 2021/1/23 7:58 下午
 * @Description 官网订单业务
 **/
public interface OrderMapper {

    /**
     * 根据订单主建，获取订单详情
     *
     * @param enter
     * @return
     */
    OrderDetailsResult getOrderDetails(IdEnter enter);

}