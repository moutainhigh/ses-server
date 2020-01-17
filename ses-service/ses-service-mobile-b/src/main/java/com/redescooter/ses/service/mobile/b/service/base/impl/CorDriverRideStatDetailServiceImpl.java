package com.redescooter.ses.service.mobile.b.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverRideStatDetailMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStatDetail;
import com.redescooter.ses.service.mobile.b.service.base.CorDriverRideStatDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorDriverRideStatDetailServiceImpl extends ServiceImpl<CorDriverRideStatDetailMapper, CorDriverRideStatDetail> implements CorDriverRideStatDetailService {

    @Override
    public int updateBatch(List<CorDriverRideStatDetail> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorDriverRideStatDetail> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorDriverRideStatDetail record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorDriverRideStatDetail record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

