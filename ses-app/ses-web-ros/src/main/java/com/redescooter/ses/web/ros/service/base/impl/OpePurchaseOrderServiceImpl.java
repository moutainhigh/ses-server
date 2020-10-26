package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePurchaseOrderMapper;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpePurchaseOrder;
import com.redescooter.ses.web.ros.service.base.OpePurchaseOrderService;

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

