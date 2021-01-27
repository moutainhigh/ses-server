package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;

/**
 * @author assert
 * @date 2021/1/27 22:52
 */
public interface OpeOutWhouseOrderMapper extends BaseMapper<OpeOutWhouseOrder> {
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
    int insertSelective(OpeOutWhouseOrder record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OpeOutWhouseOrder selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeOutWhouseOrder record);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeOutWhouseOrder record);
}