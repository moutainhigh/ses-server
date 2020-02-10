package com.redescooter.ses.service.mobile.b.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorExpressOrderMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrder;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorExpressOrderServiceImpl extends ServiceImpl<CorExpressOrderMapper, CorExpressOrder> implements CorExpressOrderService {

    @Override
    public int updateBatch(List<CorExpressOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<CorExpressOrder> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<CorExpressOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorExpressOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorExpressOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




