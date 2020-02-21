package com.redescooter.ses.service.mobile.b.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorExpressDeliveryMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDelivery;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressDeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorExpressDeliveryServiceImpl extends ServiceImpl<CorExpressDeliveryMapper, CorExpressDelivery> implements CorExpressDeliveryService {

    @Override
    public int updateBatch(List<CorExpressDelivery> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<CorExpressDelivery> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<CorExpressDelivery> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorExpressDelivery record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorExpressDelivery record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}






