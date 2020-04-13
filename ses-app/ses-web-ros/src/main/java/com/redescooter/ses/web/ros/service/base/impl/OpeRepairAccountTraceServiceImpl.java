package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairAccountTrace;
import com.redescooter.ses.web.ros.dao.base.OpeRepairAccountTraceMapper;
import com.redescooter.ses.web.ros.service.base.OpeRepairAccountTraceService;

@Service
public class OpeRepairAccountTraceServiceImpl extends ServiceImpl<OpeRepairAccountTraceMapper, OpeRepairAccountTrace> implements OpeRepairAccountTraceService {

    @Override
    public int updateBatch(List<OpeRepairAccountTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRepairAccountTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRepairAccountTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRepairAccountTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
