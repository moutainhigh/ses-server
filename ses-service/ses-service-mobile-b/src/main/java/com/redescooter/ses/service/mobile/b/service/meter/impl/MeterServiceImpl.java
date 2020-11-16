package com.redescooter.ses.service.mobile.b.service.meter.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterDeliveryOrderReuslt;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterExpressOrderResult;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterOrderEnter;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriver;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrder;
import com.redescooter.ses.service.mobile.b.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.mobile.b.service.base.CorDriverScooterService;
import com.redescooter.ses.service.mobile.b.service.base.CorDriverService;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressOrderService;
import com.redescooter.ses.service.mobile.b.service.meter.MeterService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName:MeterServiceImpl
 * @description: MeterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/16 17:54 
 */
@Service
public class MeterServiceImpl implements MeterService {

    @Reference
    private ScooterService  scooterService;

    @Autowired
    private CorDriverScooterService corDriverScooterService;

    @Autowired
    private CorDriverService corDriverService;

    @Autowired
    private CorExpressOrderService corExpressOrderService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/16 5:38 下午
     * @Param: enter
     * @Return:
     * @desc: 快递仪表当前正在进行的订单
     * @param enter
     */
    @Override
    public MeterExpressOrderResult meterExpressOrder(MeterOrderEnter enter) {
        CorDriver corDriver = this.getDriverByScooterNo(enter);

        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/16 5:47 下午
     * @Param: enter
     * @Return: MeterDeliveryOrderReuslt
     * @desc: 餐厅仪表当前正在进行的订单
     * @param enter
     */
    @Override
    public MeterDeliveryOrderReuslt meterDeliveryOrder(MeterOrderEnter enter) {
        return null;
    }
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/16 5:55 下午
    * @Param:  enter
    * @Return: CorDriver
    * @desc: 根据车辆信息获取司机信息
    */
    private CorDriver getDriverByScooterNo(MeterOrderEnter enter){
        BaseScooterResult baseScooterResult = scooterService.scooterInfoByScooterNo(enter.getId(), enter.getSn());

        QueryWrapper<CorDriverScooter> corDriverScooterQueryWrapper = new QueryWrapper<>();
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_SCOOTER_ID,baseScooterResult.getId());
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_STATUS, DriverScooterStatusEnums.USED.getValue());
        corDriverScooterQueryWrapper.last("limit 1");
        CorDriverScooter corDriverScooter = corDriverScooterService.getOne(corDriverScooterQueryWrapper);
        if (corDriverScooter==null){
            throw new MobileBException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }
        CorDriver corDriver = corDriverService.getById(corDriverScooter.getDriverId());
        return corDriver;
    }
}
