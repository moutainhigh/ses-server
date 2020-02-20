package com.redescooter.ses.service.mobile.c.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.vo.QueryDriverScooterResult;
import com.redescooter.ses.api.mobile.c.service.IdScooterService;
import com.redescooter.ses.api.mobile.c.vo.ScooterNgvEnter;
import com.redescooter.ses.api.scooter.service.ScooterIotService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @ClassName:IdScooterServiceImpl
 * @description: IdScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 11:14
 */
@Service
public class IdScooterServiceImpl implements IdScooterService {

    @Reference
    private ScooterIotService scooterIotService;

    @Reference
    private CusotmerScooterService cusotmerScooterService;

    /**
     * @param enter
     * @desc: 开启关闭导航
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/20 11:11
     * @Version: SAAS 1.2
     */
    @Override
    public GeneralResult scooterNgv(ScooterNgvEnter enter) {
        //todo 车辆数据骑行的问题
        // 查询TOC 车辆分配信息
        QueryDriverScooterResult queryDriverScooterResult = cusotmerScooterService.queryDriverScooter(enter);

        IotScooterEnter iotScooterEnter = new IotScooterEnter();
        BeanUtils.copyProperties(enter, iotScooterEnter);
        iotScooterEnter.setId(queryDriverScooterResult.getScooterId());
        iotScooterEnter.setEvent(enter.getEvent());
        iotScooterEnter.setLatitude(new BigDecimal(enter.getLat()));
        iotScooterEnter.setLongitude(new BigDecimal(enter.getLng()));
        iotScooterEnter.setBluetoothCommunication(enter.getBluetoothCommunication());
        return scooterIotService.navigation(iotScooterEnter);
    }
}
