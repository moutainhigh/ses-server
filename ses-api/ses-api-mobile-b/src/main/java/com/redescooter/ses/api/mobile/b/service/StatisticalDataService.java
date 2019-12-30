package com.redescooter.ses.api.mobile.b.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.b.vo.SaveDriverRideStatEnter;
import com.redescooter.ses.api.mobile.b.vo.SaveScooterRideStatEnter;

/**
 * @ClassName:StatisticalDataService
 * @description: StatisticalDataService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 16:51
 */
public interface StatisticalDataService {
    /**
     * 保存司机骑行数据
     *
     * @param enter
     * @return
     */
    GeneralResult saveDriverRideStat(SaveDriverRideStatEnter enter);

    /**
     * 车辆统计数据
     *
     * @param enter
     * @return
     */
    GeneralResult saveScooterRideStat(SaveScooterRideStatEnter enter);
}
