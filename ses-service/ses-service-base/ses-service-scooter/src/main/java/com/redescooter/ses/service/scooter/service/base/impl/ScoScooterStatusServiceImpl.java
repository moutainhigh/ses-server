package com.redescooter.ses.service.scooter.service.base.impl;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.redescooter.ses.service.scooter.dao.base.ScoScooterStatusMapper;
import com.redescooter.ses.service.scooter.service.base.ScoScooterStatusService;
@Service
public class ScoScooterStatusServiceImpl extends ServiceImpl<ScoScooterStatusMapper, ScoScooterStatus> implements ScoScooterStatusService{

    @Override
    public int updateBatch(List<ScoScooterStatus> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<ScoScooterStatus> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(ScoScooterStatus record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(ScoScooterStatus record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
