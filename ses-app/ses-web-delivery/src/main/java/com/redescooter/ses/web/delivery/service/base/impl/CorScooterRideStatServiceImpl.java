package com.redescooter.ses.web.delivery.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.delivery.dao.base.CorScooterRideStatMapper;
import com.redescooter.ses.web.delivery.dm.CorScooterRideStat;
import com.redescooter.ses.web.delivery.service.base.CorScooterRideStatService;
@Service
public class CorScooterRideStatServiceImpl extends ServiceImpl<CorScooterRideStatMapper, CorScooterRideStat> implements CorScooterRideStatService{

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
