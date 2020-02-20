package com.redescooter.ses.service.mobile.c.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.mobile.c.dao.base.ConDriverRideStatDetailMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStatDetail;
import com.redescooter.ses.service.mobile.c.service.base.ConDriverRideStatDetailService;
@Service
public class ConDriverRideStatDetailServiceImpl extends ServiceImpl<ConDriverRideStatDetailMapper, ConDriverRideStatDetail> implements ConDriverRideStatDetailService{

    @Override
    public int updateBatch(List<ConDriverRideStatDetail> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<ConDriverRideStatDetail> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(ConDriverRideStatDetail record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(ConDriverRideStatDetail record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
