package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStatDetail;

/**
 * @author assert
 * @date 2020/11/25 14:09
 */
public interface ScooterRideStatDetailMapper {

    /**
     * 新增车辆骑行统计数据详情
     * @param scooterRideStatDetail
     * @return int
     * @author assert
     * @date 2020/11/25
    */
    int insertScooterRideStatDetail(CorScooterRideStatDetail scooterRideStatDetail);

}
