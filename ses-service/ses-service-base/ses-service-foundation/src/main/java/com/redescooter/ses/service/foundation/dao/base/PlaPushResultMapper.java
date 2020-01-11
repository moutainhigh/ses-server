package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaPushResult;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaPushResultMapper extends BaseMapper<PlaPushResult> {
    int updateBatch(List<PlaPushResult> list);

    int updateBatchSelective(List<PlaPushResult> list);

    int batchInsert(@Param("list") List<PlaPushResult> list);

    int insertOrUpdate(PlaPushResult record);

    int insertOrUpdateSelective(PlaPushResult record);
}