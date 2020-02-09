package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.vo.StartEnter;
import com.redescooter.ses.api.mobile.b.vo.express.OrderResult;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EdOrderServiceMapper {
    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 16:09
     * @Param: enter
     * @Return: List<OrderResult>
     * @desc: 订单列表
     */
    List<OrderResult> orderList(GeneralEnter enter);

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/6 17:39
     * @Param: enter
     * @Return: int
     * @desc: 查询正在进行的订单
     */
    int dirverShippingOrder(StartEnter enter);

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/8 17:55
     * @Param: userId, TenantId
     * @Return: Long
     * @desc: 获取正在骑行的scooterId
     */
    CorDriverScooter queryScooterIdByUserId(@Param("userId") Long userId, @Param("tenantId") Long tenantId);
}
