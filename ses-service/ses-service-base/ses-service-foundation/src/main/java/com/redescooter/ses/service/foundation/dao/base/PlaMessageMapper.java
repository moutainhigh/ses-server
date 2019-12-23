package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMessage;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaMessageMapper extends BaseMapper<PlaMessage> {
    int updateBatch(List<PlaMessage> list);

    int batchInsert(@Param("list") List<PlaMessage> list);

    int insertOrUpdate(PlaMessage record);

    int insertOrUpdateSelective(PlaMessage record);

    int insertOrUpdateWithBLOBs(PlaMessage record);
}