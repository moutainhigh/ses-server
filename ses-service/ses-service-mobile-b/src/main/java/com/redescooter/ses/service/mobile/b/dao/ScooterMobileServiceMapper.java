package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName:ScooterMobileServiceMapper
 * @description: ScooterMobileServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/02 11:37
 */
public interface ScooterMobileServiceMapper {
    /**
     * 查询车辆分配信息
     *
     * @param userId
     * @return
     */
    CorDriverScooter driverScooterByUserId(@Param("userId") Long userId, @Param("scooterStatus") String scooterStatus);
}
