package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseScooterB;

/**
 * @author assert
 * @date 2021/1/13 15:53
 */
public interface OpeInWhouseScooterBService {

    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeInWhouseScooterB record);

    OpeInWhouseScooterB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeInWhouseScooterB record);

    int updateByPrimaryKey(OpeInWhouseScooterB record);
}







