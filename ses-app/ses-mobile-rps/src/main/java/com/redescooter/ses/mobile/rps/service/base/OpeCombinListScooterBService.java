package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeCombinListScooterB;

/**
 * @author assert
 * @date 2021/1/27 14:12
 */
public interface OpeCombinListScooterBService {


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeCombinListScooterB record);

    OpeCombinListScooterB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeCombinListScooterB record);

    int updateByPrimaryKey(OpeCombinListScooterB record);

}

