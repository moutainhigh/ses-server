package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeQcPartsB;
    /**
 *@author assert
 *@date 2021/1/26 15:38
 */
public interface OpeQcPartsBService{


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeQcPartsB record);

    OpeQcPartsB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeQcPartsB record);

    int updateByPrimaryKey(OpeQcPartsB record);

}
