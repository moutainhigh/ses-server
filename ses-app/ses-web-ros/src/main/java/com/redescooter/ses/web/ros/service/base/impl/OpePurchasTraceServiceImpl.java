package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpePurchasTraceMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasTrace;
import com.redescooter.ses.web.ros.service.base.OpePurchasTraceService;

@Service
public class OpePurchasTraceServiceImpl extends ServiceImpl<OpePurchasTraceMapper, OpePurchasTrace> implements OpePurchasTraceService {

    @Override
    public int updateBatch(List<OpePurchasTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
