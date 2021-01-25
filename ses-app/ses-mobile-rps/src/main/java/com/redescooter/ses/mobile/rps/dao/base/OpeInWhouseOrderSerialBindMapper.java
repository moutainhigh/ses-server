package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind;

/**
 * @author assert
 * @date 2021/1/25 15:09
 */
public interface OpeInWhouseOrderSerialBindMapper extends BaseMapper<OpeInWhouseOrderSerialBind> {
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
    int insertSelective(OpeInWhouseOrderSerialBind record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OpeInWhouseOrderSerialBind selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeInWhouseOrderSerialBind record);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeInWhouseOrderSerialBind record);
}