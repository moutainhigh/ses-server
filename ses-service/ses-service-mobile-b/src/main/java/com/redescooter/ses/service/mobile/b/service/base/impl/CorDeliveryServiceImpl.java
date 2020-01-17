package com.redescooter.ses.service.mobile.b.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorDeliveryMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDelivery;
import com.redescooter.ses.service.mobile.b.service.base.CorDeliveryService;

@Service
public class CorDeliveryServiceImpl extends ServiceImpl<CorDeliveryMapper, CorDelivery> implements CorDeliveryService {

    @Override
    public int updateBatch(List<CorDelivery> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<CorDelivery> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<CorDelivery> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorDelivery record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorDelivery record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
