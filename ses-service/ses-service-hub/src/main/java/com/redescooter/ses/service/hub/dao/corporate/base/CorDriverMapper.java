package com.redescooter.ses.service.hub.dao.corporate.base;

import com.redescooter.ses.service.hub.dm.corporate.base.CorDriver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CorDriverMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(CorDriver record);

    int insertOrUpdate(CorDriver record);

    int insertOrUpdateSelective(CorDriver record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(CorDriver record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    CorDriver selectByPrimaryKey(Long id);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(CorDriver record);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(CorDriver record);

    int updateBatch(List<CorDriver> list);

    int batchInsert(@Param("list") List<CorDriver> list);
}