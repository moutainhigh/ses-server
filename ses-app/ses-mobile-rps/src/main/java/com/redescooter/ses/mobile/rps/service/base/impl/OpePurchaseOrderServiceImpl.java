package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchaseOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchaseOrder;
import com.redescooter.ses.mobile.rps.service.base.OpePurchaseOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpePurchaseOrderServiceImpl extends ServiceImpl<OpePurchaseOrderMapper, OpePurchaseOrder> implements OpePurchaseOrderService {

    @Override
    public int updateBatch(List<OpePurchaseOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchaseOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchaseOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchaseOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
