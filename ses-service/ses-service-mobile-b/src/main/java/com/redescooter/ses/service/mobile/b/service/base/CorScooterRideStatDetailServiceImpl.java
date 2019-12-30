package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorScooterRideStatDetailMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStatDetail;
import com.redescooter.ses.service.mobile.b.service.base.impl.CorScooterRideStatDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorScooterRideStatDetailServiceImpl extends ServiceImpl<CorScooterRideStatDetailMapper, CorScooterRideStatDetail> implements CorScooterRideStatDetailService {

    @Override
    public int updateBatch(List<CorScooterRideStatDetail> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorScooterRideStatDetail> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorScooterRideStatDetail record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorScooterRideStatDetail record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

