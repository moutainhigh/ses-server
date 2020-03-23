package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpePurchasingBQcTrace;
import com.redescooter.ses.web.ros.dao.base.OpePurchasingBQcTraceMapper;
import com.redescooter.ses.web.ros.service.base.OpePurchasingBQcTraceService;

@Service
public class OpePurchasingBQcTraceServiceImpl extends ServiceImpl<OpePurchasingBQcTraceMapper, OpePurchasingBQcTrace> implements OpePurchasingBQcTraceService {

    @Override
    public int updateBatch(List<OpePurchasingBQcTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasingBQcTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasingBQcTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasingBQcTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

