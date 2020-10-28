package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeOpTraceMapper;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeOpTrace;
import com.redescooter.ses.web.ros.service.base.OpeOpTraceService;

@Service
public class OpeOpTraceServiceImpl extends ServiceImpl<OpeOpTraceMapper, OpeOpTrace> implements OpeOpTraceService {

    @Override
    public int updateBatch(List<OpeOpTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOpTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOpTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOpTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


