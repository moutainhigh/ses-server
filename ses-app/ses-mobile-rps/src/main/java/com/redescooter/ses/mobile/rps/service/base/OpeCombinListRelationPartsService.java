package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeCombinListRelationParts;
    /**
 *@author assert
 *@date 2021/1/27 17:36
 */
public interface OpeCombinListRelationPartsService{


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeCombinListRelationParts record);

    OpeCombinListRelationParts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeCombinListRelationParts record);

    int updateByPrimaryKey(OpeCombinListRelationParts record);

}
