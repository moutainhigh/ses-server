package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeProductionCombinBom;

/**
*@author assert
*@date 2021/1/8 13:48
*/
public interface OpeProductionCombinBomMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeProductionCombinBom record);

    int insertSelective(OpeProductionCombinBom record);

    OpeProductionCombinBom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeProductionCombinBom record);

    int updateByPrimaryKey(OpeProductionCombinBom record);
}