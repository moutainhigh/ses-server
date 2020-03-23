package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpePurchasingTraceMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasingTrace;
import com.redescooter.ses.web.ros.service.base.OpePurchasingTraceService;

@Service
public class OpePurchasingTraceServiceImpl extends ServiceImpl<OpePurchasingTraceMapper, OpePurchasingTrace> implements OpePurchasingTraceService {

    @Override
    public int updateBatch(List<OpePurchasingTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasingTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasingTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasingTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

