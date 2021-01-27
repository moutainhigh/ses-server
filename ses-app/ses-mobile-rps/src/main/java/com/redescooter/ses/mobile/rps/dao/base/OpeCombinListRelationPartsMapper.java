package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListRelationParts;

/**
 *@author assert
 *@date 2021/1/27 17:36
 */
public interface OpeCombinListRelationPartsMapper extends BaseMapper<OpeCombinListRelationParts> {
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
    int insertSelective(OpeCombinListRelationParts record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    OpeCombinListRelationParts selectByPrimaryKey(Long id);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeCombinListRelationParts record);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeCombinListRelationParts record);
}