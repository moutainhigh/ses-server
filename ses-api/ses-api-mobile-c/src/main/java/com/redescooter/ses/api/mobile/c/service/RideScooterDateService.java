package com.redescooter.ses.api.mobile.c.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.c.vo.SaveRideDateEnter;

/**
 * @ClassName:RideScooterDateService
 * @description: RideScooterDateService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 16:02
 */
public interface RideScooterDateService {
    /**
     * @desc: 司机骑行数据
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/21 11:45
     * @Version: APP 1.2
     */
    GeneralResult saveDriverRideDate(SaveRideDateEnter enter);

    /**
     * @desc: 车辆骑行数据
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/21 11:45
     * @Version: APP 1.2
     */
    GeneralResult saveScooterRideDate(SaveRideDateEnter enter);
}
