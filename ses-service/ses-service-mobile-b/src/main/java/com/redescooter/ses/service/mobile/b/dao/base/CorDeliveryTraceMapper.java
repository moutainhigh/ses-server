package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDeliveryTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorDeliveryTraceMapper extends BaseMapper<CorDeliveryTrace> {
    int batchInsert(@Param("list") List<CorDeliveryTrace> list);

    int insertOrUpdate(CorDeliveryTrace record);

    int insertOrUpdateSelective(CorDeliveryTrace record);
}