package com.redescooter.ses.service.scooter.dao.base;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterBbiBatteryWare;

/**
 * @author assert
 * @date 2020/11/20 16:36
 */
public interface ScoScooterBbiBatteryWareMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ScoScooterBbiBatteryWare record);

    int insertSelective(ScoScooterBbiBatteryWare record);

    ScoScooterBbiBatteryWare selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScoScooterBbiBatteryWare record);

    int updateByPrimaryKey(ScoScooterBbiBatteryWare record);

}