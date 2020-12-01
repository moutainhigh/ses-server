package com.redescooter.ses.service.mobile.c.dao;

import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStatDetail;

/**
 * @author assert
 * @date 2020/11/26 13:21
 */
public interface ScooterRideStatDetailMapper {

    /**
     * 新增车辆骑行统计数据详情
     * @param scooterRideStatDetail
     * @return int
     * @author assert
     * @date 2020/11/25
     */
    int insertScooterRideStatDetail(ConScooterRideStatDetail scooterRideStatDetail);

}
