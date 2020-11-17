package com.redescooter.ses.api.mobile.b.service.meter;

import com.redescooter.ses.api.mobile.b.vo.meter.MeterDeliveryOrderReuslt;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterExpressOrderResult;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterOrderEnter;

/**
 * @ClassName:MeterService
 * @description: MeterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/17 10:51 
 */
public interface MeterService {
    /**
     * @Description
     * @Author: alex
     * @Date:   2020/11/16 5:38 下午
     * @Param:  enter
     * @Return:
     * @desc: 快递仪表当前正在进行的订单
     */
    MeterExpressOrderResult meterExpressOrder(MeterOrderEnter enter);
    /**
     * @Description
     * @Author: alex
     * @Date:   2020/11/16 5:47 下午
     * @Param:  enter
     * @Return: MeterDeliveryOrderReuslt
     * @desc: 餐厅仪表当前正在进行的订单
     */
    MeterDeliveryOrderReuslt meterDeliveryOrder(MeterOrderEnter enter);
}
