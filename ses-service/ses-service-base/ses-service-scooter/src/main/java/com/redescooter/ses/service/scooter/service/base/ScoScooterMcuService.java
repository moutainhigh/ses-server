package com.redescooter.ses.service.scooter.service.base;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterMcu;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
public interface ScoScooterMcuService{


    int deleteByPrimaryKey(Long id);

    int insert(ScoScooterMcu record);

    int insertSelective(ScoScooterMcu record);

    ScoScooterMcu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScoScooterMcu record);

    int updateByPrimaryKey(ScoScooterMcu record);

}
