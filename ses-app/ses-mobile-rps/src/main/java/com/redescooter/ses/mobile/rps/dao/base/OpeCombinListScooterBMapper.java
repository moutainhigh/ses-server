package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListScooterB;

/**
 * @author assert
 * @date 2021/1/27 19:01
 */
public interface OpeCombinListScooterBMapper extends BaseMapper<OpeCombinListScooterB> {
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
    int insertSelective(OpeCombinListScooterB record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OpeCombinListScooterB selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeCombinListScooterB record);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeCombinListScooterB record);
}