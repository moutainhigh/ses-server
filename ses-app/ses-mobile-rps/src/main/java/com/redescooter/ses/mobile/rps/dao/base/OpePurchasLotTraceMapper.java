package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasLotTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasLotTraceMapper extends BaseMapper<OpePurchasLotTrace> {
    int updateBatch(List<OpePurchasLotTrace> list);

    int batchInsert(@Param("list") List<OpePurchasLotTrace> list);

    int insertOrUpdate(OpePurchasLotTrace record);

    int insertOrUpdateSelective(OpePurchasLotTrace record);
}