package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStatDetail;

/**
 * @author assert
 * @date 2020/11/25 14:08
 */
public interface DriverRideStatDetailMapper {

    /**
     * 新增司机骑行统计数据详情
     * @param driverRideStatDetail
     * @return int
     * @author assert
     * @date 2020/11/25
     */
    int insertDriverRideStatDetail(CorDriverRideStatDetail driverRideStatDetail);

}
