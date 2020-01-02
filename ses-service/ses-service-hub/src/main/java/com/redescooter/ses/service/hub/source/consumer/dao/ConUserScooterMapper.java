package com.redescooter.ses.service.hub.source.consumer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.consumer.dm.ConUserScooter;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConUserScooterMapper extends BaseMapper<ConUserScooter> {
    int updateBatch(List<ConUserScooter> list);

    int batchInsert(@Param("list") List<ConUserScooter> list);

    int insertOrUpdate(ConUserScooter record);

    int insertOrUpdateSelective(ConUserScooter record);

    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(ConUserScooter record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(ConUserScooter record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    ConUserScooter selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ConUserScooter record);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ConUserScooter record);
}