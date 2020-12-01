package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionPurchaseOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionPurchaseOrderMapper extends BaseMapper<OpeProductionPurchaseOrder> {
    int updateBatch(List<OpeProductionPurchaseOrder> list);

    int batchInsert(@Param("list") List<OpeProductionPurchaseOrder> list);

    int insertOrUpdate(OpeProductionPurchaseOrder record);

    int insertOrUpdateSelective(OpeProductionPurchaseOrder record);
}