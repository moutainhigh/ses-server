package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind;

/**
 * @author assert
 * @date 2021/1/22 17:47
 */
public interface OpeInWhouseOrderSerialBindService {


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeInWhouseOrderSerialBind record);

    OpeInWhouseOrderSerialBind selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeInWhouseOrderSerialBind record);

    int updateByPrimaryKey(OpeInWhouseOrderSerialBind record);

}

