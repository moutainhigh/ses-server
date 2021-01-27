package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListPartsSerialBind;

/**
 * @author assert
 * @date 2021/1/27 20:15
 */
public interface OpeCombinListPartsSerialBindMapper extends BaseMapper<OpeCombinListPartsSerialBind> {
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
    int insertSelective(OpeCombinListPartsSerialBind record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OpeCombinListPartsSerialBind selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeCombinListPartsSerialBind record);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeCombinListPartsSerialBind record);
}