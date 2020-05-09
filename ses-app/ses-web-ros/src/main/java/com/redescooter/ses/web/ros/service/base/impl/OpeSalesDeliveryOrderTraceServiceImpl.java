package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeSalesDeliveryOrderTraceMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrderTrace;
import com.redescooter.ses.web.ros.service.base.OpeSalesDeliveryOrderTraceService;

@Service
public class OpeSalesDeliveryOrderTraceServiceImpl extends ServiceImpl<OpeSalesDeliveryOrderTraceMapper, OpeSalesDeliveryOrderTrace> implements OpeSalesDeliveryOrderTraceService {

    @Override
    public int updateBatch(List<OpeSalesDeliveryOrderTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSalesDeliveryOrderTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSalesDeliveryOrderTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSalesDeliveryOrderTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
