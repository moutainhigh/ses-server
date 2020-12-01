package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStat;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2020/11/25 11:14
 */
public interface DriverRideStatMapper {

    /**
     * 新增司机骑行统计数据
     * @param driverRideStat
     * @return int
     * @author assert
     * @date 2020/11/25
    */
    int insertDriverRideStat(CorDriverRideStat driverRideStat);

    /**
     * 修改司机骑行统计数据
     * @param driverRideStat
     * @return int
     * @author assert
     * @date 2020/11/25
    */
    int updateDriverRideStat(CorDriverRideStat driverRideStat);

    /**
     * 根据tenantId、driverId查询司机骑行统计数据
     * @param tenantId, driverId
     * @return com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStat
     * @author assert
     * @date 2020/11/25
    */
    CorDriverRideStat getDriverRideStatByTenantIdAndDriverId(@Param("tenantId") Long tenantId, @Param("driverId") Long driverId);

}
