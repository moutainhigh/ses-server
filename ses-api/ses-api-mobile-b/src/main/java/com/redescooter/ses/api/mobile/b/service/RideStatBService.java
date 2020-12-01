package com.redescooter.ses.api.mobile.b.service;


import com.redescooter.ses.api.common.vo.scooter.InsertRideStatDataDTO;

/**
 * 骑行数据业务接口--2B端
 * @author assert
 * @date 2020/11/25 11:19
 */
public interface RideStatBService {

    /**
     * 保存司机/车辆骑行统计数据和详情
     * @param rideStatData
     * @return int
     * @author assert
     * @date 2020/11/25
     */
    int insertDriverAndScooterRideStat(InsertRideStatDataDTO rideStatData);

}
