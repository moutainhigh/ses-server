package com.redescooter.ses.service.mobile.b.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorDeliveryTraceMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDeliveryTrace;
import com.redescooter.ses.service.mobile.b.service.base.CorDeliveryTraceService;
import org.springframework.stereotype.Service;

import java.util.List;

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

