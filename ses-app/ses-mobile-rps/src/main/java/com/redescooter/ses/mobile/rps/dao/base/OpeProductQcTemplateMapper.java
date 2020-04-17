package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductQcTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductQcTemplateMapper extends BaseMapper<OpeProductQcTemplate> {
    int updateBatch(List<OpeProductQcTemplate> list);

    int batchInsert(@Param("list") List<OpeProductQcTemplate> list);

    int insertOrUpdate(OpeProductQcTemplate record);

    int insertOrUpdateSelective(OpeProductQcTemplate record);
}