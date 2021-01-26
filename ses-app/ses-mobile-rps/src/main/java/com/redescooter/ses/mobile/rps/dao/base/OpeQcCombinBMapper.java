package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcCombinB;

/**
 *@author assert
 *@date 2021/1/26 15:38
 */
public interface OpeQcCombinBMapper extends BaseMapper<OpeQcCombinB> {
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
    int insertSelective(OpeQcCombinB record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    OpeQcCombinB selectByPrimaryKey(Long id);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeQcCombinB record);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeQcCombinB record);
}