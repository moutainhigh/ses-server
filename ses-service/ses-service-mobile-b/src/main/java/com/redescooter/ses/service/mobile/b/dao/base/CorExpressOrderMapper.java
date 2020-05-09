package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorExpressOrderMapper extends BaseMapper<CorExpressOrder> {
    int updateBatch(List<CorExpressOrder> list);

    int updateBatchSelective(List<CorExpressOrder> list);

    int batchInsert(@Param("list") List<CorExpressOrder> list);

    int insertOrUpdate(CorExpressOrder record);

    int insertOrUpdateSelective(CorExpressOrder record);
}