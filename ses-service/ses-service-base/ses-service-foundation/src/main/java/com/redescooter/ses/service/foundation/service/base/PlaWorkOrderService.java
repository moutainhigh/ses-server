package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaWorkOrder;

import java.util.List;

public interface PlaWorkOrderService extends IService<PlaWorkOrder> {


    int updateBatch(List<PlaWorkOrder> list);

    int updateBatchSelective(List<PlaWorkOrder> list);

    int batchInsert(List<PlaWorkOrder> list);

    int insertOrUpdate(PlaWorkOrder record);

    int insertOrUpdateSelective(PlaWorkOrder record);

}
