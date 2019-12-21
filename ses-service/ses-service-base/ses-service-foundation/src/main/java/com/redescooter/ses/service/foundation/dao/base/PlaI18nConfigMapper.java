package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaI18nConfig;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaI18nConfigMapper extends BaseMapper<PlaI18nConfig> {
    int updateBatch(List<PlaI18nConfig> list);

    int batchInsert(@Param("list") List<PlaI18nConfig> list);

    int insertOrUpdate(PlaI18nConfig record);

    int insertOrUpdateSelective(PlaI18nConfig record);
}