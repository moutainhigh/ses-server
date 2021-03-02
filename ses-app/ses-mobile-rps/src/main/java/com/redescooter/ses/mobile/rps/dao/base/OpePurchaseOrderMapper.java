package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchaseOrder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchaseOrderMapper extends BaseMapper<OpePurchaseOrder> {
    int updateBatch(List<OpePurchaseOrder> list);

    int batchInsert(@Param("list") List<OpePurchaseOrder> list);

    int insertOrUpdate(OpePurchaseOrder record);

    int insertOrUpdateSelective(OpePurchaseOrder record);
}