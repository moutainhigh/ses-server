package com.redescooter.ses.service.mobile.b.service.meter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.vo.express.OrderResult;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterDeliveryOrderReuslt;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterExpressOrderResult;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterOrderEnter;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:MeterService
 * @description: MeterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/16 17:28 
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
