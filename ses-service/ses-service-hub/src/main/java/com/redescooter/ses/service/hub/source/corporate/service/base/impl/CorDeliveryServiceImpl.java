package com.redescooter.ses.service.hub.source.corporate.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.corporate.dao.CorDeliveryMapper;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDelivery;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorDeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;

@DS("corporate")
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



