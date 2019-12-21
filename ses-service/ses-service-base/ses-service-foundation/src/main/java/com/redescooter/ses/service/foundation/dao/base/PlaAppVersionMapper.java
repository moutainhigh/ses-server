package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaAppVersionMapper extends BaseMapper<PlaAppVersion> {
    int updateBatch(List<PlaAppVersion> list);

    int batchInsert(@Param("list") List<PlaAppVersion> list);

    int insertOrUpdate(PlaAppVersion record);

    int insertOrUpdateSelective(PlaAppVersion record);
}