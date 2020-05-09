package com.redescooter.ses.web.delivery.service.base;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CorExpressOrderService extends IService<CorExpressOrder> {


    int updateBatch(List<CorExpressOrder> list);

    int updateBatchSelective(List<CorExpressOrder> list);

    int batchInsert(List<CorExpressOrder> list);

    int insertOrUpdate(CorExpressOrder record);

    int insertOrUpdateSelective(CorExpressOrder record);

}
