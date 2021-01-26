package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeQcOrderSerialBind;

/**
 * @author assert
 * @date 2021/1/25 10:02
 */
public interface OpeQcOrderSerialBindService {


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeQcOrderSerialBind record);

    OpeQcOrderSerialBind selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeQcOrderSerialBind record);

    int updateByPrimaryKey(OpeQcOrderSerialBind record);

}


