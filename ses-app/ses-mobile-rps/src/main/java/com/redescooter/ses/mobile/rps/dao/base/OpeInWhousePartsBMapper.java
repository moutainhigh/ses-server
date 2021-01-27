package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;

/**
 * @author assert
 * @date 2021/1/27 11:54
 */
public interface OpeInWhousePartsBMapper extends BaseMapper<OpeInWhousePartsB> {
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
    int insertSelective(OpeInWhousePartsB record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OpeInWhousePartsB selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeInWhousePartsB record);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeInWhousePartsB record);
}