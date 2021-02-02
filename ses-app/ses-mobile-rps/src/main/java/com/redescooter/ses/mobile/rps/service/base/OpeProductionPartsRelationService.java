package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeProductionPartsRelation;
    /**
 *@author assert
 *@date 2021/1/27 21:34
 */
public interface OpeProductionPartsRelationService{


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeProductionPartsRelation record);

    OpeProductionPartsRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeProductionPartsRelation record);

    int updateByPrimaryKey(OpeProductionPartsRelation record);

}
