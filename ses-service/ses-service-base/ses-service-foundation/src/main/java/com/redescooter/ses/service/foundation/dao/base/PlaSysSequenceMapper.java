package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaSysSequence;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaSysSequenceMapper extends BaseMapper<PlaSysSequence> {
    int updateBatch(List<PlaSysSequence> list);

    int batchInsert(@Param("list") List<PlaSysSequence> list);

    int insertOrUpdate(PlaSysSequence record);

    int insertOrUpdateSelective(PlaSysSequence record);
}