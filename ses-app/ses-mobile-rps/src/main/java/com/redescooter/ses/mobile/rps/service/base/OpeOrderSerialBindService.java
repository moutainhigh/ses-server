package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind;

/**
 * @author assert
 * @date 2020/12/30 15:26
 */
public interface OpeOrderSerialBindService {


    int deleteByPrimaryKey(Long id);

    int insert(OpeOrderSerialBind record);

    int insertSelective(OpeOrderSerialBind record);

    OpeOrderSerialBind selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeOrderSerialBind record);

    int updateByPrimaryKey(OpeOrderSerialBind record);

}

