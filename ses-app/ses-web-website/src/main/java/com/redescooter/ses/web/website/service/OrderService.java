package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IdResult;
import com.redescooter.ses.web.website.vo.order.AddUpdateOrderEnter;
import com.redescooter.ses.web.website.vo.order.AddOrderPartsEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;

import java.util.List;

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
    IdResult addOrder(AddUpdateOrderEnter enter);

    /**
     * 创建订单配件
     *
     * @param enter
     * @return
     */
    GeneralResult AddOrderParts(AddOrderPartsEnter enter);

    /**
     * 客户订单列表
     *
     * @param enter
     * @return
     */
    List<OrderDetailsResult> getOrderList(GeneralEnter enter);

    /**
     * @param enter
     * @return
     */
    OrderDetailsResult getOrderDetails(IdEnter enter);

}
