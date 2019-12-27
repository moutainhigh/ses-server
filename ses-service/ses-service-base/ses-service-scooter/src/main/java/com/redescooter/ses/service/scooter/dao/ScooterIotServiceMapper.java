package com.redescooter.ses.service.scooter.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @ClassName:ScooterIotServiceMapper
 * @description: ScooterIotServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 17:51
 */
public interface ScooterIotServiceMapper {

    void updateScooterLock(@Param("lock") String lock, @Param("userId") Long userId, @Param("id") Long id);
}
