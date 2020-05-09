package com.redescooter.ses.web.delivery.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.delivery.dao.base.CorExpressOrderTraceMapper;
import com.redescooter.ses.web.delivery.dm.CorExpressOrderTrace;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderTraceService;

@Service
public class CorExpressOrderTraceServiceImpl extends ServiceImpl<CorExpressOrderTraceMapper, CorExpressOrderTrace> implements CorExpressOrderTraceService {

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
