package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchaseOrder;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchaseOrderMapper extends BaseMapper<OpePurchaseOrder> {
    int updateBatch(List<OpePurchaseOrder> list);

    int batchInsert(@Param("list") List<OpePurchaseOrder> list);

    int insertOrUpdate(OpePurchaseOrder record);

    int insertOrUpdateSelective(OpePurchaseOrder record);
}