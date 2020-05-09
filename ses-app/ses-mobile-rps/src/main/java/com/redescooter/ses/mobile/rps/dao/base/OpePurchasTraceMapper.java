package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasTraceMapper extends BaseMapper<OpePurchasTrace> {
    int updateBatch(List<OpePurchasTrace> list);

    int batchInsert(@Param("list") List<OpePurchasTrace> list);

    int insertOrUpdate(OpePurchasTrace record);

    int insertOrUpdateSelective(OpePurchasTrace record);
}