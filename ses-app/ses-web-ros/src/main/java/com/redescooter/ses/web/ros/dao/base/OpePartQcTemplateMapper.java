package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartQcTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartQcTemplateMapper extends BaseMapper<OpePartQcTemplate> {
    int updateBatch(List<OpePartQcTemplate> list);

    int batchInsert(@Param("list") List<OpePartQcTemplate> list);

    int insertOrUpdate(OpePartQcTemplate record);

    int insertOrUpdateSelective(OpePartQcTemplate record);
}