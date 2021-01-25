package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IdResult;
import com.redescooter.ses.web.website.vo.order.AddOrderEnter;
import com.redescooter.ses.web.website.vo.order.AddOrderPartsEnter;
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
    IdResult addOrder(AddOrderEnter enter);


    /**
     * 创建订单配件
     *
     * @param enter
     * @return
     */
    GeneralResult AddOrderParts(AddOrderPartsEnter enter);

    /**
     * 获取订单详情
     *
     * @param enter
     */
    OrderDetailsResult getOrderDetails(IdEnter enter);

    /**
     * @param enter
     * @return
     */
    OrderDetailsResult getOrderDetailsByMyself(GeneralEnter enter);

}
