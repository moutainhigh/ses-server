package com.redescooter.ses.service.mobile.b.service.base.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorExpressOrderTraceMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderTrace;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressOrderTraceService;

@Service
public class CorExpressOrderTraceServiceImpl extends ServiceImpl<CorExpressOrderTraceMapper, CorExpressOrderTrace> implements CorExpressOrderTraceService {

    @Override
    public int updateBatch(List<CorExpressOrderTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<CorExpressOrderTrace> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<CorExpressOrderTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorExpressOrderTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorExpressOrderTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




