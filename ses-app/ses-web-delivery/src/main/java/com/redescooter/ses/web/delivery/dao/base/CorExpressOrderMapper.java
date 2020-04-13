package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CorExpressOrderMapper extends BaseMapper<CorExpressOrder> {
    int updateBatch(List<CorExpressOrder> list);

    int updateBatchSelective(List<CorExpressOrder> list);

    int batchInsert(@Param("list") List<CorExpressOrder> list);

    int insertOrUpdate(CorExpressOrder record);

    int insertOrUpdateSelective(CorExpressOrder record);
}