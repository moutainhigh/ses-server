package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasQcTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasQcTraceMapper extends BaseMapper<OpePurchasQcTrace> {
    int updateBatch(List<OpePurchasQcTrace> list);

    int batchInsert(@Param("list") List<OpePurchasQcTrace> list);

    int insertOrUpdate(OpePurchasQcTrace record);

    int insertOrUpdateSelective(OpePurchasQcTrace record);
}