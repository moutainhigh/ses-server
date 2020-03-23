package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasingTrace;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchasingTraceMapper extends BaseMapper<OpePurchasingTrace> {
    int updateBatch(List<OpePurchasingTrace> list);

    int batchInsert(@Param("list") List<OpePurchasingTrace> list);

    int insertOrUpdate(OpePurchasingTrace record);

    int insertOrUpdateSelective(OpePurchasingTrace record);
}