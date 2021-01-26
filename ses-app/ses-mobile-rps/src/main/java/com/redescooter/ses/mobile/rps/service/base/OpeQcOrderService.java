package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeQcOrder;
    /**
 *@author assert
 *@date 2021/1/26 10:56
 */
public interface OpeQcOrderService{


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeQcOrder record);

    OpeQcOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeQcOrder record);

    int updateByPrimaryKey(OpeQcOrder record);

}
