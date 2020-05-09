package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasBQcTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasBQcTraceMapper extends BaseMapper<OpePurchasBQcTrace> {
    int updateBatch(List<OpePurchasBQcTrace> list);

    int batchInsert(@Param("list") List<OpePurchasBQcTrace> list);

    int insertOrUpdate(OpePurchasBQcTrace record);

    int insertOrUpdateSelective(OpePurchasBQcTrace record);
}