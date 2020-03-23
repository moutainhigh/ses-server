package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasingBQcTrace;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchasingBQcTraceMapper extends BaseMapper<OpePurchasingBQcTrace> {
    int updateBatch(List<OpePurchasingBQcTrace> list);

    int batchInsert(@Param("list") List<OpePurchasingBQcTrace> list);

    int insertOrUpdate(OpePurchasingBQcTrace record);

    int insertOrUpdateSelective(OpePurchasingBQcTrace record);
}