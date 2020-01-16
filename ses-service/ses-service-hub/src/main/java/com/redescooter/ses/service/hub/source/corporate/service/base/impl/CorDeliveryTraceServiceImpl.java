package com.redescooter.ses.service.hub.source.corporate.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorDeliveryTraceService;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDeliveryTrace;
import com.redescooter.ses.service.hub.source.corporate.dao.CorDeliveryTraceMapper;

@DS("corporate")
@Service
public class CorDeliveryTraceServiceImpl extends ServiceImpl<CorDeliveryTraceMapper, CorDeliveryTrace> implements CorDeliveryTraceService {

    @Override
    public int batchInsert(List<CorDeliveryTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorDeliveryTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorDeliveryTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
