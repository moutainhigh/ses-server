package com.redescooter.ses.admin.dev.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.admin.dev.dm.AdmWorkOrder;

import java.util.List;
public interface AdmWorkOrderService extends IService<AdmWorkOrder> {


    int updateBatch(List<AdmWorkOrder> list);

    int updateBatchSelective(List<AdmWorkOrder> list);

    int batchInsert(List<AdmWorkOrder> list);

    int insertOrUpdate(AdmWorkOrder record);

    int insertOrUpdateSelective(AdmWorkOrder record);

}
