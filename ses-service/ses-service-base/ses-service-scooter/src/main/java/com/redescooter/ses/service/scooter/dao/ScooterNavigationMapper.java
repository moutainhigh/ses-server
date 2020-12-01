package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterNavigation;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2020/11/25 18:57
 */
public interface ScooterNavigationMapper {

    /**
     * 新增车辆导航信息
     * @param scooterNavigation
     * @return int
     * @author assert
     * @date 2020/11/25
    */
    int insertScooterNavigation(ScoScooterNavigation scooterNavigation);

    /**
     * 修改车辆导航信息
     * @param scooterNavigation
     * @return int
     * @author assert
     * @date 2020/11/25
    */
    int updateScooterNavigation(ScoScooterNavigation scooterNavigation);

    /**
     * 根据scooterId、status查询车辆导航信息
     * @param scooterId, status
     * @return com.redescooter.ses.service.scooter.dm.base.ScoScooterNavigation
     * @author assert
     * @date 2020/11/25
    */
    ScoScooterNavigation getScooterNavigationByScooterIdAndStatus(@Param("scooterId") Long scooterId, @Param("status") String status);

}
