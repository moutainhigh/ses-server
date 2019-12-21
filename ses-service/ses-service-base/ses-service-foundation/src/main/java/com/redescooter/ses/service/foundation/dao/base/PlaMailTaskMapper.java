package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTask;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaMailTaskMapper extends BaseMapper<PlaMailTask> {
    int updateBatch(List<PlaMailTask> list);

    int batchInsert(@Param("list") List<PlaMailTask> list);

    int insertOrUpdate(PlaMailTask record);

    int insertOrUpdateSelective(PlaMailTask record);
}