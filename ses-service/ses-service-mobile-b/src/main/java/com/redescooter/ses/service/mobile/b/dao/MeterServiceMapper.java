package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.api.mobile.b.vo.meter.MeterDeliveryOrderReuslt;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterExpressOrderResult;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName:MeterServiceMapper
 * @description: MeterServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/16 18:27 
 */
public interface MeterServiceMapper {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/17 9:51 上午
     * @Param: driverId, status
     * @Return: MeterExpressOrderResult
     * @desc: 查询当前司机正在进行的订单
     */
    MeterExpressOrderResult meterExpressOrderByStatus(@Param("driverId") Long driverId, @Param("status") String status);

    /**
     * @Description
     * @Author: aleax
     * @Date: 2020/11/17 10:04 上午
     * @Param: driverId
     * @Return: int
     * @desc: 仪表统计数据
     */
    int meterExpressOrderByCount(@Param("driverId") Long driverId, @Param("completeStatus") String completeStatus, @Param("rejectedStatus") String rejectedStatus);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/17 10:28 上午
     * @Param: userId、状态
     * @Return: MeterExpressOrderResult
     * @desc: 查询当前司机正在进行的订单
     */
    MeterDeliveryOrderReuslt meterDeliveryOrderByStatus(@Param("userId") Long userId, @Param("status") String status);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/17 10:47 上午
    * @Param:  userId
    * @Return: int
    * @desc: 订单统计
    */
    int meterDeliveryOrderByCount(@Param("userId") Long userId, @Param("pendingStatus") String pendingStatus, @Param("deliveryStatus") String deliveryStatus);
}
