package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeCombinListPartsSerialBind;

/**
 * @author assert
 * @date 2021/1/27 17:37
 */
public interface OpeCombinListPartsSerialBindService {


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeCombinListPartsSerialBind record);

    OpeCombinListPartsSerialBind selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeCombinListPartsSerialBind record);

    int updateByPrimaryKey(OpeCombinListPartsSerialBind record);

}

