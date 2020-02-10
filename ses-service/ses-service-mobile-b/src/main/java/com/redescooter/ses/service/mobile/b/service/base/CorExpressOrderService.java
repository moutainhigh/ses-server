package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface CorExpressOrderService extends IService<CorExpressOrder> {


    int updateBatch(List<CorExpressOrder> list);

    int updateBatchSelective(List<CorExpressOrder> list);

    int batchInsert(List<CorExpressOrder> list);

    int insertOrUpdate(CorExpressOrder record);

    int insertOrUpdateSelective(CorExpressOrder record);
}




