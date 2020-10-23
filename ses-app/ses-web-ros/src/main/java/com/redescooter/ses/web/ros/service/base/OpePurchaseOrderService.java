package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchaseOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePurchaseOrderService extends IService<OpePurchaseOrder> {


    int updateBatch(List<OpePurchaseOrder> list);

    int batchInsert(List<OpePurchaseOrder> list);

    int insertOrUpdate(OpePurchaseOrder record);

    int insertOrUpdateSelective(OpePurchaseOrder record);

}
