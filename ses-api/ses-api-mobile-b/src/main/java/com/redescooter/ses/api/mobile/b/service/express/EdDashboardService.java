package com.redescooter.ses.api.mobile.b.service.express;

import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.vo.express.EdMobileBExpressOrderChartResult;

import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/2/14 15:28
 *  @version：V 1.2
 *  @Description: 快递统计模块
 */

public interface EdDashboardService {
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/14 15:29
    * @Param:  enter
    * @Return: map
    * @desc: 所有的订单统计
    */
    Map<String,Integer> allCountByStatus(GeneralEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/14 15:48
    * @Param:  enter
    * @Return: MobileBDeliveryChartResult
    * @desc:  订单月统计
    */
    EdMobileBExpressOrderChartResult mobileBExpressOrderChart(DateTimeParmEnter enter);
}
