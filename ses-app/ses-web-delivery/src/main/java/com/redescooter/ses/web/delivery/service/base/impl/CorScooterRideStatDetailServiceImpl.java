package com.redescooter.ses.web.delivery.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.delivery.dao.base.CorScooterRideStatDetailMapper;
import java.util.List;
import com.redescooter.ses.web.delivery.dm.CorScooterRideStatDetail;
import com.redescooter.ses.web.delivery.service.base.CorScooterRideStatDetailService;
@Service
public class CorScooterRideStatDetailServiceImpl extends ServiceImpl<CorScooterRideStatDetailMapper, CorScooterRideStatDetail> implements CorScooterRideStatDetailService{

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
