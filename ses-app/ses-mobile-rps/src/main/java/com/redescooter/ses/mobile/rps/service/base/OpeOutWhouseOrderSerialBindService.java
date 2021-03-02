package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrderSerialBind;

/**
 * @author assert
 * @date 2021/1/25 10:46
 */
public interface OpeOutWhouseOrderSerialBindService {


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeOutWhouseOrderSerialBind record);

    OpeOutWhouseOrderSerialBind selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeOutWhouseOrderSerialBind record);

    int updateByPrimaryKey(OpeOutWhouseOrderSerialBind record);

}


