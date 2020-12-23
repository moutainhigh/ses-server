package com.redescooter.ses.api.mobile.b.service.meter;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterDeliveryOrderReuslt;
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
    MeterDeliveryOrderReuslt meterExpressOrder(IdEnter enter);
    /**
     * @Description
     * @Author: alex
     * @Date:   2020/11/16 5:47 下午
     * @Param:  enter
     * @Return: MeterDeliveryOrderReuslt
     * @desc: 餐厅仪表当前正在进行的订单
     */
    MeterDeliveryOrderReuslt meterDeliveryOrder(IdEnter enter);
    /**
    * @Description
    * @Author: aleax
    * @Date:   2020/11/17 2:59 下午
    * @Param:  enter
    * @Return: MeterDeliveryOrderReuslt
    * @desc: 仪表订单
    */
    MeterDeliveryOrderReuslt meterOrder(MeterOrderEnter enter);

    /**
     * 同步订单数量到平板
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2020/12/18
    */
    GeneralResult syncOrderQuantity(MeterOrderEnter enter);

}
