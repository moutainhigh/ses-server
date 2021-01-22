package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockSerialNumber;

/**
 *@author assert
 *@date 2021/1/22 20:10
 */
public interface OpeWmsStockSerialNumberMapper extends BaseMapper<OpeWmsStockSerialNumber> {
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
    int insertSelective(OpeWmsStockSerialNumber record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    OpeWmsStockSerialNumber selectByPrimaryKey(Long id);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OpeWmsStockSerialNumber record);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OpeWmsStockSerialNumber record);
}