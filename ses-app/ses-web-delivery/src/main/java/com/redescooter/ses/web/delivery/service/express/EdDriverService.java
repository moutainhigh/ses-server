package com.redescooter.ses.web.delivery.service.express;


import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.delivery.vo.AssignScooterEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryChartEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryChartListResult;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyResult;

public interface EdDriverService {
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/5 14:43
    * @Param:  enter
    * @Return: PageResult<DeliveryHistroyResult>
     * @desc: 司机历史订单
     */
    PageResult<DeliveryHistroyResult> expressOrderHistroy(DeliveryHistroyEnter enter);


    /**
     * 快递司机详情单据柱状图
     *
     * @param enter
     * @return
     */
    DeliveryChartListResult eDDriverCharts(DeliveryChartEnter enter);

    /**
     * 司机车辆分配
     *
     * @param enter
     * @return
     */
    GeneralResult assignScooter(AssignScooterEnter enter);

    /**
     * 司机归还车辆
     *
     * @param enter
     * @return
     */
    GeneralResult removeScooter(IdEnter enter);
}
