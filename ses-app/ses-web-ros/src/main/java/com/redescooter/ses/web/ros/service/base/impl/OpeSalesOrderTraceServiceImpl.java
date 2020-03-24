package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeSalesOrderTraceMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesOrderTrace;
import com.redescooter.ses.web.ros.service.base.OpeSalesOrderTraceService;

@Service
public class OpeSalesOrderTraceServiceImpl extends ServiceImpl<OpeSalesOrderTraceMapper, OpeSalesOrderTrace> implements OpeSalesOrderTraceService {

    @Override
    public int updateBatch(List<OpeSalesOrderTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSalesOrderTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSalesOrderTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSalesOrderTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
