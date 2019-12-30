package com.redescooter.ses.service.mobile.b.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStat;
import java.util.List;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverRideStatMapper;
import com.redescooter.ses.service.mobile.b.service.base.impl.CorDriverRideStatService;
@Service
public class CorDriverRideStatServiceImpl extends ServiceImpl<CorDriverRideStatMapper, CorDriverRideStat> implements CorDriverRideStatService{

    @Override
    public int updateBatch(List<CorDriverRideStat> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<CorDriverRideStat> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(CorDriverRideStat record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(CorDriverRideStat record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
