package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorExpressOrderTraceMapper extends BaseMapper<CorExpressOrderTrace> {
    int updateBatch(List<CorExpressOrderTrace> list);

    int updateBatchSelective(List<CorExpressOrderTrace> list);

    int batchInsert(@Param("list") List<CorExpressOrderTrace> list);

    int insertOrUpdate(CorExpressOrderTrace record);

    int insertOrUpdateSelective(CorExpressOrderTrace record);
}