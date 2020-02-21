package com.redescooter.ses.service.mobile.c.service;

import java.math.BigDecimal;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.redescooter.ses.api.common.enums.base.BizType;
import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.vo.QueryDriverScooterResult;
import com.redescooter.ses.api.mobile.c.exception.MobileCException;
import com.redescooter.ses.api.mobile.c.service.IdScooterService;
import com.redescooter.ses.api.mobile.c.service.RideScooterDateService;
import com.redescooter.ses.api.mobile.c.vo.SaveRideDateEnter;
import com.redescooter.ses.api.mobile.c.vo.ScooterNavigationEnter;
import com.redescooter.ses.api.scooter.service.ScooterIotService;
import com.redescooter.ses.service.mobile.c.exception.ExceptionCodeEnums;
import com.redescooter.ses.tool.utils.StringUtils;

/**
 * @ClassName:IdScooterServiceImpl
 * @description: IdScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 11:14
 */
@Service
public class IdScooterServiceImpl implements IdScooterService {

    @Autowired
    private RideScooterDateService rideScooterDateService;

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
    @Transactional
    @Override
    public GeneralResult scooterNavigation(ScooterNavigationEnter enter) {
        // 查询TOC 车辆分配信息
        QueryDriverScooterResult queryDriverScooterResult = cusotmerScooterService.queryDriverScooter(enter);

        if (enter.getEvent().equals(CommonEvent.END.getValue())) {
            if (StringUtils.isBlank(enter.getMileage())){
                throw new MobileCException(ExceptionCodeEnums.MILEAGE_IS_EMPTY.getCode(),ExceptionCodeEnums.MILEAGE_IS_EMPTY.getMessage());
            }
            if (enter.getDuration()==null || enter.getDuration()==0){
                throw new MobileCException(ExceptionCodeEnums.DURATION_IS_EMPTY.getCode(),ExceptionCodeEnums.DURATION_IS_EMPTY.getMessage());
            }
            SaveRideDateEnter saveRideDateEnter=new SaveRideDateEnter();
            BeanUtils.copyProperties(enter,saveRideDateEnter);
            saveRideDateEnter.setMileage(new BigDecimal(enter.getMileage()));
            saveRideDateEnter.setDuration(enter.getDuration());
            saveRideDateEnter.setBizId(queryDriverScooterResult.getScooterId());
            saveRideDateEnter.setBizType(BizType.SCOOTER.getValue());
            // 保存骑行数据
            rideScooterDateService.saveDriverRideDate(saveRideDateEnter);
            rideScooterDateService.saveScooterRideDate(saveRideDateEnter);
        }
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
