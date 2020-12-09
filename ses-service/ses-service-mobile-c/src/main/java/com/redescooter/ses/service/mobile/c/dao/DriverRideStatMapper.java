package com.redescooter.ses.service.mobile.c.dao;

import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStat;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2020/11/26 13:20
 */
public interface DriverRideStatMapper {

    /**
     * 新增司机骑行统计数据
     * @param driverRideStat
     * @return int
     * @author assert
     * @date 2020/11/25
     */
    int insertDriverRideStat(ConDriverRideStat driverRideStat);

    /**
     * 修改司机骑行统计数据
     * @param driverRideStat
     * @return int
     * @author assert
     * @date 2020/11/25
     */
    int updateDriverRideStat(ConDriverRideStat driverRideStat);

    /**
     * 根据tenantId、driverId查询司机骑行统计数据
     * @param tenantId, driverId
     * @return com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStat
     * @author assert
     * @date 2020/11/25
     */
    ConDriverRideStat getDriverRideStatByTenantIdAndDriverId(@Param("tenantId") Long tenantId, @Param("driverId") Long driverId);

}
