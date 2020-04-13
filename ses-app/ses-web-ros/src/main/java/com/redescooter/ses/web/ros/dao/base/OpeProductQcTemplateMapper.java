package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplate;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductQcTemplateMapper extends BaseMapper<OpeProductQcTemplate> {
    int updateBatch(List<OpeProductQcTemplate> list);

    int batchInsert(@Param("list") List<OpeProductQcTemplate> list);

    int insertOrUpdate(OpeProductQcTemplate record);

    int insertOrUpdateSelective(OpeProductQcTemplate record);
}