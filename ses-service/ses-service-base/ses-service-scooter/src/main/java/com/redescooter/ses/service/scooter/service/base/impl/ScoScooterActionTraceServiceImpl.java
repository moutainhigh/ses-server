package com.redescooter.ses.service.scooter.service.base.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterActionTraceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterActionTrace;
import com.redescooter.ses.service.scooter.service.base.ScoScooterActionTraceService;
@Service
public class ScoScooterActionTraceServiceImpl extends ServiceImpl<ScoScooterActionTraceMapper, ScoScooterActionTrace> implements ScoScooterActionTraceService{

    @Override
    public int updateBatch(List<ScoScooterActionTrace> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<ScoScooterActionTrace> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(ScoScooterActionTrace record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(ScoScooterActionTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
