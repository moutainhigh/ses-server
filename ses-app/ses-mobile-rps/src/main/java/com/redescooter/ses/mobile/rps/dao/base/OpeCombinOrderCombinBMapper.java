package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderCombinB;

/**
 *@author assert
 *@date 2021/1/27 21:09
 */
public interface OpeCombinOrderCombinBMapper extends BaseMapper<OpeCombinOrderCombinB> {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OpeCombinOrderCombinB record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    OpeCombinOrderCombinB selectByPrimaryKey(Integer id);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeCombinOrderCombinB record);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeCombinOrderCombinB record);
}