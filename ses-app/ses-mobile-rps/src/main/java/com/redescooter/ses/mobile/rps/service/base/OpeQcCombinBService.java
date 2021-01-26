package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeQcCombinB;
    /**
 *@author assert
 *@date 2021/1/26 15:38
 */
public interface OpeQcCombinBService{


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeQcCombinB record);

    OpeQcCombinB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeQcCombinB record);

    int updateByPrimaryKey(OpeQcCombinB record);

}
