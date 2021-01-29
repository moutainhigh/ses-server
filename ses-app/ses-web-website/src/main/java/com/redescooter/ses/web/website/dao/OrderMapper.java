package com.redescooter.ses.web.website.dao;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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


    /**
     * 通过订单的ID获取model
     * @param orderId
     * @return
     */
    String getProductModelByOrderId(@Param("orderId") Long orderId);


    /**
     * 获取订单列表
     *
     * @param enter
     * @return
     */
    List<OrderDetailsResult> getOrderlist(GeneralEnter enter);
}
