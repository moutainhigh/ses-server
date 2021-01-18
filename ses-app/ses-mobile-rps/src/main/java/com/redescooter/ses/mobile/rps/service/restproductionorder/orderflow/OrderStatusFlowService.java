package com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowResult;

import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/10/27 18:59
 *  @version：V ROS 1.8. *  @Description:
 */
public interface OrderStatusFlowService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 19:05
     * @Param: enter
     * @Return: OrderStatusFlowResult
     * @desc: 订单节点
     */
    List<OrderStatusFlowResult> listBybussId(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 19:06
     * @Param:
     * @Return: OrderStatusFlowResult
     * @desc: 查询所有节点
     */
    List<OrderStatusFlowResult> list();

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 19:07
     * @Param: enter
     * @Return: 订单节点详情
     * @desc: 订单节点详情
     */
    OrderStatusFlowResult detail(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 19:10
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存节点
     */
    GeneralResult save(OrderStatusFlowEnter enter);

    /**
     * 新增订单状态流转记录
     * @param orderId, orderType, orderStatus, remark, userId
     * @return int
     * @author assert
     * @date 2021/1/18
    */
    int insertOrderStatusFlow(Long orderId, Integer orderType, Integer orderStatus, String remark, Long userId);

}
