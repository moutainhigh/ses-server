package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowResult;

import java.util.List;

public interface OrderStatusFlowServiceMapper {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 19:18
     * @Param: enter
     * @Return: OrderStatusFlowResult
     * @desc: 列表
     */
    List<OrderStatusFlowResult> listBybussId(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 19:27
     * @Param: enter
     * @Return: OrderStatusFlowResult
     * @desc: 节点详情
     */
    OrderStatusFlowResult detail(IdEnter enter);
}
