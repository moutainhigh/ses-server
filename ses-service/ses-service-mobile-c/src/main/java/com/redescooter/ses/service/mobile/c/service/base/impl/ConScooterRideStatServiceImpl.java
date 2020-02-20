package com.redescooter.ses.service.mobile.c.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStat;
import java.util.List;
import com.redescooter.ses.service.mobile.c.dao.base.ConScooterRideStatMapper;
import com.redescooter.ses.service.mobile.c.service.base.ConScooterRideStatService;
@Service
public class ConScooterRideStatServiceImpl extends ServiceImpl<ConScooterRideStatMapper, ConScooterRideStat> implements ConScooterRideStatService{

    @Override
    public int updateBatch(List<ConScooterRideStat> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<ConScooterRideStat> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(ConScooterRideStat record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(ConScooterRideStat record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
