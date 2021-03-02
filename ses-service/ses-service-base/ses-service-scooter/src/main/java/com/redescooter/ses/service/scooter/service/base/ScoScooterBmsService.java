package com.redescooter.ses.service.scooter.service.base;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterBms;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
public interface ScoScooterBmsService {


    int deleteByPrimaryKey(Long id);

    int insert(ScoScooterBms record);

    int insertSelective(ScoScooterBms record);

    ScoScooterBms selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScoScooterBms record);

    int updateByPrimaryKey(ScoScooterBms record);

}

