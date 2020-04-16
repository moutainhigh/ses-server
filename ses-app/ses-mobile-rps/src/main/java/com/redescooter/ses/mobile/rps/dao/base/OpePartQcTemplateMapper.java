package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpePartQcTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartQcTemplateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpePartQcTemplate record);

    int insertOrUpdate(OpePartQcTemplate record);

    int insertOrUpdateSelective(OpePartQcTemplate record);

    int insertSelective(OpePartQcTemplate record);

    OpePartQcTemplate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpePartQcTemplate record);

    int updateByPrimaryKey(OpePartQcTemplate record);

    int updateBatch(List<OpePartQcTemplate> list);

    int updateBatchSelective(List<OpePartQcTemplate> list);

    int batchInsert(@Param("list") List<OpePartQcTemplate> list);
}