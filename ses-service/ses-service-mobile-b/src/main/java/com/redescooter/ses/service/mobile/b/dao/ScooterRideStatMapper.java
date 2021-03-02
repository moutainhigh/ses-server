package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStat;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2020/11/25 11:22
 */
public interface ScooterRideStatMapper {

    /**
     * 新增车辆骑行统计数据
     * @param scooterRideStat
     * @return int
     * @author assert
     * @date 2020/11/25
    */
    int insertScooterRideStat(CorScooterRideStat scooterRideStat);

    /**
     * 修改车辆骑行统计数据
     * @param scooterRideStat
     * @return int
     * @author assert
     * @date 2020/11/25
    */
    int updateScooterRideStat(CorScooterRideStat scooterRideStat);

    /**
     * 根据tenantId、scooterId查询车辆骑行统计数据
     * @param tenantId, scooterId
     * @return com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStat
     * @author assert
     * @date 2020/11/25
    */
    CorScooterRideStat getScooterRideStatByTenantIdAndScooterId(@Param("tenantId") Long tenantId,
                                                                @Param("scooterId") Long scooterId);

}
