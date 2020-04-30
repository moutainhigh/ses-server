package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasLotTrace;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpePurchasLotTraceMapper extends BaseMapper<OpePurchasLotTrace> {
    int updateBatch(List<OpePurchasLotTrace> list);

    int updateBatchSelective(List<OpePurchasLotTrace> list);
}