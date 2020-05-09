package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.vo.express.EdMonthlyExpressOrderChartResult;

import java.util.List;

public interface EdDashboardServiceMapper {
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/14 15:56
    * @Param:  enter
    * @Return: CountByStatusResult
    * @desc:  所有订单状态统计
    */
    List<CountByStatusResult> allCountByStatus(GeneralEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/14 20:22
    * @Param:  enter
    * @Return: CountByStatusResult
    * @desc: 拒绝订单统计
    */
    CountByStatusResult refuseCount(GeneralEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/14 20:57
    * @Param:  enter
    * @Return: MonthlyDeliveryChartResult
    * @desc: 订单统计图表
    */
    List<EdMonthlyExpressOrderChartResult> mobileBDeliveryChart(DateTimeParmEnter enter);
}
