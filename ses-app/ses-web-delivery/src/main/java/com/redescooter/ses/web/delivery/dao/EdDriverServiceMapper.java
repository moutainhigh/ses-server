package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.web.delivery.vo.DeliveryHistroyEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyResult;

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
    * @Author:  AlexLi
    * @Date:   2020/2/4 13:13
    * @Param:  enter
    * @Return: List<DeliveryHistroyResult>
    * @desc: 快递司机历史订单列表
    */
    List<DeliveryHistroyResult> expressOrderHistroyList(DeliveryHistroyEnter enter);
}
