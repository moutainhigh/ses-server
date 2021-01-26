package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeQcScooterB;
    /**
 *@author assert
 *@date 2021/1/26 15:38
 */
public interface OpeQcScooterBService{


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeQcScooterB record);

    OpeQcScooterB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeQcScooterB record);

    int updateByPrimaryKey(OpeQcScooterB record);

}
