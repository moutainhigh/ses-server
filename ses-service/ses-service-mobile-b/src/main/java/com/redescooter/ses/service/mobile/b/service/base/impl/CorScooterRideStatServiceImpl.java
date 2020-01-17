package com.redescooter.ses.service.mobile.b.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorScooterRideStatMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStat;
import com.redescooter.ses.service.mobile.b.service.base.CorScooterRideStatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorScooterRideStatServiceImpl extends ServiceImpl<CorScooterRideStatMapper, CorScooterRideStat> implements CorScooterRideStatService {

    @Override
    public int updateBatch(List<CorScooterRideStat> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorScooterRideStat> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorScooterRideStat record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorScooterRideStat record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

