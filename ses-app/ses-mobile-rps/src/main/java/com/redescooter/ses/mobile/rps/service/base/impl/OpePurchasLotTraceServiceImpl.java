package com.redescooter.ses.mobile.rps.service.base.impl;

import com.redescooter.ses.mobile.rps.dm.OpePurchasLotTrace;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchasLotTraceMapper;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasLotTraceService;

@Service
public class OpePurchasLotTraceServiceImpl extends ServiceImpl<OpePurchasLotTraceMapper, OpePurchasLotTrace> implements OpePurchasLotTraceService {

    @Override
    public int updateBatch(List<OpePurchasLotTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasLotTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasLotTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasLotTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int updateBatchSelective(List<OpePurchasLotTrace> list) {
        return baseMapper.updateBatchSelective(list);
    }
}



