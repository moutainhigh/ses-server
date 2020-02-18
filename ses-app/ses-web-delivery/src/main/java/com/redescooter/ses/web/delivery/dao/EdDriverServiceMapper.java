package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyResult;

import java.math.BigDecimal;
import java.util.List;

public interface EdDriverServiceMapper {
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/4 13:12
    * @Param:  enter
    * @Return: int
    * @desc: 快递司机历史订单列表
    */
    int expressOrderHistroy(DeliveryHistroyEnter enter);

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/4 13:13
     * @Param: enter
     * @Return: List<DeliveryHistroyResult>
     * @desc: 快递司机历史订单列表
     */
    List<DeliveryHistroyResult> expressOrderHistroyList(DeliveryHistroyEnter enter);

    /**
     * @desc: 查询今日司机未完成的订单
     * @paam: enter
     * @retrn: int
     * @auther: alex
     * @date: 2020/2/18 19:09
     * @Version: SAAS 1.2
     */
    int queryTodayDriverUncompletedOrder(IdEnter enter);

    /**
     * @desc: 查询车辆今日里程
     * @param: a
     * @retrn: BigDecimal
     * @auther: alex
     * @date: 2020/2/18 19:16
     * @Version: SAAS 1.2
     */
    BigDecimal queryScooterMileage(IdEnter enter);
}
