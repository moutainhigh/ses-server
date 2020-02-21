package com.redescooter.ses.service.mobile.c.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.mobile.c.dao.base.ConScooterRideStatDetailMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStatDetail;
import com.redescooter.ses.service.mobile.c.service.base.ConScooterRideStatDetailService;

@Service
public class ConScooterRideStatDetailServiceImpl extends ServiceImpl<ConScooterRideStatDetailMapper, ConScooterRideStatDetail> implements ConScooterRideStatDetailService {

    @Override
    public int updateBatch(List<ConScooterRideStatDetail> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<ConScooterRideStatDetail> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(ConScooterRideStatDetail record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(ConScooterRideStatDetail record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

