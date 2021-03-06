package com.redescooter.ses.web.delivery.service.base.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.delivery.dao.base.CorExpressDeliveryMapper;
import com.redescooter.ses.web.delivery.dm.CorExpressDelivery;
import com.redescooter.ses.web.delivery.service.base.CorExpressDeliveryService;

@Service
public class CorExpressDeliveryServiceImpl extends ServiceImpl<CorExpressDeliveryMapper, CorExpressDelivery> implements CorExpressDeliveryService {

    @Override
    public int updateBatch(List<CorExpressDelivery> list) {
        return baseMapper.updateBatch(list);
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

