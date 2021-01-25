package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrderSerialBind;

/**
 * @author assert
 * @date 2021/1/25 15:28
 */
public interface OpeOutWhouseOrderSerialBindMapper extends BaseMapper<OpeOutWhouseOrderSerialBind> {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(OpeOutWhouseOrderSerialBind record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OpeOutWhouseOrderSerialBind selectByPrimaryKey(Integer id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeOutWhouseOrderSerialBind record);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeOutWhouseOrderSerialBind record);
}