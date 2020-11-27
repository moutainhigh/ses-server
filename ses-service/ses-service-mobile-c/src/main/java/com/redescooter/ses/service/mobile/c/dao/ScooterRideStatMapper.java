package com.redescooter.ses.service.mobile.c.dao;

import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStat;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2020/11/26 13:21
 */
public interface ScooterRideStatMapper {

    /**
     * 新增车辆骑行统计数据
     * @param scooterRideStat
     * @return int
     * @author assert
     * @date 2020/11/25
     */
    int insertScooterRideStat(ConScooterRideStat scooterRideStat);

    /**
     * 修改车辆骑行统计数据
     * @param scooterRideStat
     * @return int
     * @author assert
     * @date 2020/11/25
     */
    int updateScooterRideStat(ConScooterRideStat scooterRideStat);

    /**
     * 根据tenantId、scooterId查询车辆骑行统计数据
     * @param tenantId, scooterId
     * @return com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStat
     * @author assert
     * @date 2020/11/25
     */
    ConScooterRideStat getScooterRideStatByTenantIdAndScooterId(@Param("tenantId") Long tenantId,
                                                                @Param("scooterId") Long scooterId);

}
