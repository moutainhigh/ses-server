package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasQcTrace;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpePurchasQcTraceMapper extends BaseMapper<OpePurchasQcTrace> {
    int updateBatch(List<OpePurchasQcTrace> list);

    int updateBatchSelective(List<OpePurchasQcTrace> list);

    int batchInsert(@Param("list") List<OpePurchasQcTrace> list);

    int insertOrUpdate(OpePurchasQcTrace record);

    int insertOrUpdateSelective(OpePurchasQcTrace record);
}