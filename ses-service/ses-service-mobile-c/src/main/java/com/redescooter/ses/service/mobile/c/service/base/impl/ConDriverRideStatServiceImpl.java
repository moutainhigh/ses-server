package com.redescooter.ses.service.mobile.c.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStat;
import com.redescooter.ses.service.mobile.c.dao.base.ConDriverRideStatMapper;
import com.redescooter.ses.service.mobile.c.service.base.ConDriverRideStatService;

@Service
public class ConDriverRideStatServiceImpl extends ServiceImpl<ConDriverRideStatMapper, ConDriverRideStat> implements ConDriverRideStatService {

    @Override
    public int updateBatch(List<ConDriverRideStat> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<ConDriverRideStat> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(ConDriverRideStat record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(ConDriverRideStat record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

