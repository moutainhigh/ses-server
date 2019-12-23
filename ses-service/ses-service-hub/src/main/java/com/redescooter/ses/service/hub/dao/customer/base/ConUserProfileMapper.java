package com.redescooter.ses.service.hub.dao.customer.base;

import com.redescooter.ses.service.hub.dm.customer.base.ConUserProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConUserProfileMapper {
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
    int insert(ConUserProfile record);

    int insertOrUpdate(ConUserProfile record);

    int insertOrUpdateSelective(ConUserProfile record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(ConUserProfile record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    ConUserProfile selectByPrimaryKey(Long id);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ConUserProfile record);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ConUserProfile record);

    int updateBatch(List<ConUserProfile> list);

    int batchInsert(@Param("list") List<ConUserProfile> list);
}