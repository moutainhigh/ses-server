package com.redescooter.ses.web.ros.dao.base;

import com.redescooter.ses.web.ros.dm.OpeProductionPartsDraft;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpeProductionPartsDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeProductionPartsDraft record);

    int insertOrUpdate(OpeProductionPartsDraft record);

    int insertOrUpdateSelective(OpeProductionPartsDraft record);

    int insertSelective(OpeProductionPartsDraft record);

    OpeProductionPartsDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeProductionPartsDraft record);

    int updateByPrimaryKey(OpeProductionPartsDraft record);

    int updateBatch(List<OpeProductionPartsDraft> list);

    int batchInsert(@Param("list") List<OpeProductionPartsDraft> list);

    int updateBatchSelective(List<OpeProductionPartsDraft> list);
}