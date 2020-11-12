package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPurchaseOrderMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionPurchaseOrder;
import com.redescooter.ses.web.ros.service.base.OpeProductionPurchaseOrderService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OpeProductionPurchaseOrderServiceImpl extends ServiceImpl<OpeProductionPurchaseOrderMapper, OpeProductionPurchaseOrder> implements OpeProductionPurchaseOrderService {

    @Override
    public int updateBatch(List<OpeProductionPurchaseOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionPurchaseOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionPurchaseOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionPurchaseOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


