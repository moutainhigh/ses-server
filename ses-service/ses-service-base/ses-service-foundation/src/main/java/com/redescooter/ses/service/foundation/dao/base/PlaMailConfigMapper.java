package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaMailConfigMapper extends BaseMapper<PlaMailConfig> {
    int updateBatch(List<PlaMailConfig> list);

    int batchInsert(@Param("list") List<PlaMailConfig> list);

    int insertOrUpdate(PlaMailConfig record);

    int insertOrUpdateSelective(PlaMailConfig record);
}