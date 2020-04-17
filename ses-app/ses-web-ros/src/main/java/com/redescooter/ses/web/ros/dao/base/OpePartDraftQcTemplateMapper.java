package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartDraftQcTemplateMapper extends BaseMapper<OpePartDraftQcTemplate> {
    int updateBatch(List<OpePartDraftQcTemplate> list);

    int batchInsert(@Param("list") List<OpePartDraftQcTemplate> list);

    int insertOrUpdate(OpePartDraftQcTemplate record);

    int insertOrUpdateSelective(OpePartDraftQcTemplate record);
}