package com.redescooter.ses.service.mobile.b.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverScooterMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import com.redescooter.ses.service.mobile.b.service.base.CorDriverScooterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorDriverScooterServiceImpl extends ServiceImpl<CorDriverScooterMapper, CorDriverScooter> implements CorDriverScooterService {

    @Override
    public int updateBatch(List<CorDriverScooter> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorDriverScooter> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorDriverScooter record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorDriverScooter record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


