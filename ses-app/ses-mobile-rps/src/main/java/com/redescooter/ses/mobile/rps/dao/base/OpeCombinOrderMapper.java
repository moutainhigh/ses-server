package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrder;

/**
 * @author assert
 * @date 2021/1/22 10:30
 */
public interface OpeCombinOrderMapper extends BaseMapper<OpeCombinOrder> {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(OpeCombinOrder record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OpeCombinOrder selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeCombinOrder record);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeCombinOrder record);

}