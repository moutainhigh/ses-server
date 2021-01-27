package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;
    /**
 *@author assert
 *@date 2021/1/27 22:52
 */
public interface OpeOutWhouseOrderService{


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeOutWhouseOrder record);

    OpeOutWhouseOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeOutWhouseOrder record);

    int updateByPrimaryKey(OpeOutWhouseOrder record);

}
