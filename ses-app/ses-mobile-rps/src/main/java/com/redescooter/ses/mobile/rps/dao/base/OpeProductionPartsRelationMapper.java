package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPartsRelation;

/**
 *@author assert
 *@date 2021/1/27 21:34
 */
public interface OpeProductionPartsRelationMapper extends BaseMapper<OpeProductionPartsRelation> {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OpeProductionPartsRelation record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    OpeProductionPartsRelation selectByPrimaryKey(Long id);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeProductionPartsRelation record);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeProductionPartsRelation record);
}