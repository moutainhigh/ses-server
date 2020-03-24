package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairOrderTrace;
import com.redescooter.ses.web.ros.dao.base.OpeRepairOrderTraceMapper;
import com.redescooter.ses.web.ros.service.base.OpeRepairOrderTraceService;

@Service
public class OpeRepairOrderTraceServiceImpl extends ServiceImpl<OpeRepairOrderTraceMapper, OpeRepairOrderTrace> implements OpeRepairOrderTraceService {

    @Override
    public int updateBatch(List<OpeRepairOrderTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRepairOrderTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRepairOrderTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRepairOrderTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
