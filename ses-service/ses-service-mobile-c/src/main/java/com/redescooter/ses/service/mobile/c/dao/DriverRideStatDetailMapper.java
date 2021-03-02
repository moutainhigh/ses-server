package com.redescooter.ses.service.mobile.c.dao;

import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStatDetail;

/**
 * @author assert
 * @date 2020/11/26 13:20
 */
public interface DriverRideStatDetailMapper {

    /**
     * 新增司机骑行统计数据详情
     * @param driverRideStatDetail
     * @return int
     * @author assert
     * @date 2020/11/25
     */
    int insertDriverRideStatDetail(ConDriverRideStatDetail driverRideStatDetail);

}
