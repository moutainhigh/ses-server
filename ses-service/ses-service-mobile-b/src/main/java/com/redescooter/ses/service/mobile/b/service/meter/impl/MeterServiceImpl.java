package com.redescooter.ses.service.mobile.b.service.meter.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.expressDelivery.ExpressDeliveryDetailStatusEnums;
import com.redescooter.ses.api.common.enums.expressOrder.ExpressOrderStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.api.mobile.b.service.meter.MeterService;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterDeliveryOrderReuslt;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterOrderEnter;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.emqx.SyncOrderQuantityPublishDTO;
import com.redescooter.ses.service.mobile.b.dao.MeterServiceMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriver;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import com.redescooter.ses.service.mobile.b.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.mobile.b.service.base.CorDriverScooterService;
import com.redescooter.ses.service.mobile.b.service.base.CorDriverService;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressOrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName:MeterServiceImpl
 * @description: MeterServiceImpl
 * @author: Alex
 * @Version???1.3
 * @create: 2020/11/16 17:54
 */
@DubboService
public class MeterServiceImpl implements MeterService {

    @DubboReference
    private ScooterService scooterService;

    @DubboReference
    private UserBaseService userBaseService;

    @DubboReference
    private ScooterEmqXService scooterEmqXService;

    @Autowired
    private CorDriverScooterService corDriverScooterService;

    @Autowired
    private CorDriverService corDriverService;

    @Autowired
    private MeterServiceMapper meterServiceMapper;

    @Autowired
    private CorExpressOrderService corExpressOrderService;

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/16 5:38 ??????
     * @Param: enter
     * @Return:
     * @desc: ???????????????????????????????????????
     */
    @Override
    public MeterDeliveryOrderReuslt meterExpressOrder(IdEnter enter) {
        //?????????????????????????????????
        MeterDeliveryOrderReuslt result = meterServiceMapper.meterExpressOrderByStatus(enter.getId(), ExpressOrderStatusEnums.SHIPPING.getValue());

        if (null == result) {
            result = new MeterDeliveryOrderReuslt();
        }

        int count = meterServiceMapper.meterExpressOrderByCount(enter.getId(), ExpressDeliveryDetailStatusEnums.COMPLETED.getValue(),
                ExpressDeliveryDetailStatusEnums.REJECTED.getValue());

        result.setRemainingOrderNum(count);

        return result;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/16 5:47 ??????
     * @Param: enter
     * @Return: MeterDeliveryOrderReuslt
     * @desc: ???????????????????????????????????????
     */
    @Override
    public MeterDeliveryOrderReuslt meterDeliveryOrder(IdEnter enter) {
        MeterDeliveryOrderReuslt result = meterServiceMapper.meterDeliveryOrderByStatus(enter.getUserId(), DeliveryStatusEnums.DELIVERING.getValue());

        if (null == result) {
            result = new MeterDeliveryOrderReuslt();
        }

        // ????????????????????????????????????
        int count = meterServiceMapper.meterDeliveryOrderByCount(enter.getUserId(), DeliveryStatusEnums.PENDING.getValue(),
                DeliveryStatusEnums.DELIVERING.getValue());
        result.setRemainingOrderNum(count);

        return result;
    }

    /**
     * @param enter
     * @Description
     * @Author: aleax
     * @Date: 2020/11/17 2:59 ??????
     * @Param: enter
     * @Return: MeterDeliveryOrderReuslt
     * @desc: ????????????
     */
    @Override
    public MeterDeliveryOrderReuslt meterOrder(MeterOrderEnter enter) {
        CorDriver corDriver = this.getDriverByScooterNo(enter);
        if (corDriver == null) {
            return null;
        }
        GeneralEnter generalEnter = new GeneralEnter();
        generalEnter.setUserId(corDriver.getUserId());
        QueryUserResult queryUserResult = userBaseService.queryUserById(generalEnter);
        if (queryUserResult == null) {
            return null;
        }
        MeterDeliveryOrderReuslt result = null;
        IdEnter idEnter = new IdEnter();
        switch (queryUserResult.getUserType()) {
            case 4:
                //??????APP???
                idEnter.setUserId(corDriver.getUserId());
                result = meterDeliveryOrder(idEnter);
                break;
            case 5:
                //??????APP???
                idEnter.setId(corDriver.getId());
                result = meterExpressOrder(idEnter);
                break;
            case 6:
                //TOC ?????????
                break;
            default:
                break;
        }

        // ??????UserId, ????????????????????????????????????
        result.setUserId(corDriver.getUserId());
        //??????????????????
        return result;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult syncOrderQuantity(MeterOrderEnter enter) {
        MeterDeliveryOrderReuslt result = meterOrder(enter);

        /**
         * ??????emq????????????????????????????????????????????????????????????????????????
         */
        SyncOrderQuantityPublishDTO publishDTO = new SyncOrderQuantityPublishDTO();
        publishDTO.setTabletSn(enter.getSn());
        publishDTO.setQuantity(result.getRemainingOrderNum());

        scooterEmqXService.syncOrderQuantity(publishDTO);

        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/16 5:55 ??????
     * @Param: enter
     * @Return: CorDriver
     * @desc: ????????????????????????????????????
     */
    private CorDriver getDriverByScooterNo(MeterOrderEnter enter) {
        /**
         * -scooterService.scooterInfoByScooterNo(enter.getId(), enter.getSn()) ????????????????????????????????????
         * -?????????????????????????????????????????????sco_scooter_status???,???????????????????????????????????????
         */
//        BaseScooterResult baseScooterResult = scooterService.scooterInfoByScooterNo(enter.getId(), enter.getSn());
        BaseScooterResult baseScooterResult = scooterService.getScooterByTabletSn(enter.getSn());
        if (null == baseScooterResult) {
            return null;
        }
        QueryWrapper<CorDriverScooter> corDriverScooterQueryWrapper = new QueryWrapper<>();
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_SCOOTER_ID, baseScooterResult.getId());
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_STATUS, DriverScooterStatusEnums.USED.getValue());
        corDriverScooterQueryWrapper.last("limit 1");
        CorDriverScooter corDriverScooter = corDriverScooterService.getOne(corDriverScooterQueryWrapper);
        if (corDriverScooter == null) {
            throw new MobileBException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(), ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }
        CorDriver corDriver = corDriverService.getById(corDriverScooter.getDriverId());
        return corDriver;
    }
}
