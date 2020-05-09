package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaMailTemplateMapper extends BaseMapper<PlaMailTemplate> {
    int batchInsert(@Param("list") List<PlaMailTemplate> list);

    int insertOrUpdate(PlaMailTemplate record);

    int insertOrUpdateSelective(PlaMailTemplate record);

    int insertOrUpdateWithBLOBs(PlaMailTemplate record);
}