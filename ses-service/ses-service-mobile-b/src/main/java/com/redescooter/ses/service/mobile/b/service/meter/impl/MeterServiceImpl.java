package com.redescooter.ses.service.mobile.b.service.meter.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.expressDelivery.ExpressDeliveryDetailStatusEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.api.mobile.b.service.meter.MeterService;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterDeliveryOrderReuslt;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterOrderEnter;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.service.mobile.b.dao.MeterServiceMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriver;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import com.redescooter.ses.service.mobile.b.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.mobile.b.service.base.CorDriverScooterService;
import com.redescooter.ses.service.mobile.b.service.base.CorDriverService;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressOrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

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
    private MeterServiceMapper meterServiceMapper;

    @Autowired
    private CorExpressOrderService corExpressOrderService;

    @Autowired
    private UserBaseService userBaseService;

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
    public MeterDeliveryOrderReuslt meterExpressOrder(IdEnter enter) {
        //查询当前正在进行的订单
        MeterDeliveryOrderReuslt result= meterServiceMapper.meterExpressOrderByStatus(enter.getId(),ExpressOrderStatusEnums.SHIPPING.getValue());
        if (result!=null){
            //查询所有订单的统计数据
            int  count=meterServiceMapper.meterExpressOrderByCount(enter.getId(), ExpressDeliveryDetailStatusEnums.COMPLETED.getValue(),ExpressDeliveryDetailStatusEnums.REJECTED.getValue());
            result.setRemainingOrderNum(count);
        }
        return result;
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
    public MeterDeliveryOrderReuslt meterDeliveryOrder(IdEnter enter) {
        MeterDeliveryOrderReuslt result= meterServiceMapper.meterDeliveryOrderByStatus(enter.getUserId(), DeliveryStatusEnums.DELIVERING.getValue());
        if (Objects.nonNull(result)) {
            result.setRemainingOrderNum(meterServiceMapper.meterDeliveryOrderByCount(enter.getUserId(),DeliveryStatusEnums.PENDING.getValue(),DeliveryStatusEnums.DELIVERING.getValue()));
        }
        return result;
    }

    /**
     * @Description
     * @Author: aleax
     * @Date: 2020/11/17 2:59 下午
     * @Param: enter
     * @Return: MeterDeliveryOrderReuslt
     * @desc: 仪表订单
     * @param enter
     */
    @Override
    public MeterDeliveryOrderReuslt meterOrder(MeterOrderEnter enter) {
        CorDriver corDriver = this.getDriverByScooterNo(enter);
        if (corDriver==null){
            return null;
        }
        GeneralEnter generalEnter = new GeneralEnter();
        generalEnter.setUserId(corDriver.getUserId());
        QueryUserResult queryUserResult = userBaseService.queryUserById(generalEnter);
        if (queryUserResult == null) {
            return null;
        }
        MeterDeliveryOrderReuslt result=null;
        IdEnter idEnter = new IdEnter();
        switch (queryUserResult.getUserType()){
            case 4:
                //餐厅APP端
                idEnter.setUserId(corDriver.getUserId());
                result = meterDeliveryOrder(idEnter);
                break;
            case 5:
                //快递APP端
                idEnter.setId(corDriver.getId());
                result = meterExpressOrder(idEnter);
                break;
            case 6:
                //TOC 个人端
                break;
            default:
                break;
        }

        //获取账户类型
        return result;
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
        if (baseScooterResult==null){
            return null;
        }
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