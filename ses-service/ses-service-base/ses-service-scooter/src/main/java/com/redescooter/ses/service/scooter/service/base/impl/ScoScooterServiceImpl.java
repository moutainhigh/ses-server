package com.redescooter.ses.service.scooter.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterMapper;
import com.redescooter.ses.service.scooter.service.base.ScoScooterService;

@Service
public class ScoScooterServiceImpl extends ServiceImpl<ScoScooterMapper, ScoScooter> implements ScoScooterService {

    @Override
    public int updateBatch(List<ScoScooter> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<ScoScooter> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(ScoScooter record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(ScoScooter record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int updateBatchSelective(List<ScoScooter> list) {
        return baseMapper.updateBatchSelective(list);
    }
}

