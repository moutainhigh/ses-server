package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeCombinListCombinB;

/**
 * @author assert
 * @date 2021/1/27 14:12
 */
public interface OpeCombinListCombinBService {


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeCombinListCombinB record);

    OpeCombinListCombinB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeCombinListCombinB record);

    int updateByPrimaryKey(OpeCombinListCombinB record);

}

