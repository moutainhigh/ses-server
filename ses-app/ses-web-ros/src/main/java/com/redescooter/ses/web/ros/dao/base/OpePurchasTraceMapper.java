package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasTraceMapper extends BaseMapper<OpePurchasTrace> {
    int updateBatch(List<OpePurchasTrace> list);

    int batchInsert(@Param("list") List<OpePurchasTrace> list);

    int insertOrUpdate(OpePurchasTrace record);

    int insertOrUpdateSelective(OpePurchasTrace record);
}