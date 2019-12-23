package com.redescooter.ses.service.hub.dao.corporate.base;

import com.redescooter.ses.service.hub.dm.corporate.base.CorUserProfile;

import java.util.List;

public interface CorUserProfileMapper {
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
    int insert(CorUserProfile record);

    int insertOrUpdate(CorUserProfile record);

    int insertOrUpdateSelective(CorUserProfile record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(CorUserProfile record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    CorUserProfile selectByPrimaryKey(Long id);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(CorUserProfile record);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(CorUserProfile record);

    int updateBatch(List<CorUserProfile> list);

}