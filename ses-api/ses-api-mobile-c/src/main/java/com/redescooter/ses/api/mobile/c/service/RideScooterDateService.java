package com.redescooter.ses.api.mobile.c.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.c.vo.SaveDriverRideDateEnter;

/**
 * @ClassName:RideScooterDateService
 * @description: RideScooterDateService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 16:02
 */
public interface RideScooterDateService {

    GeneralResult saveDriverRideDate(SaveDriverRideDateEnter enter);
}
