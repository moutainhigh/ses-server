package com.redescooter.ses.service.hub.source.consumer.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.consumer.dm.HubConUserScooter;
import com.redescooter.ses.service.hub.source.consumer.dao.ConUserScooterMapper;
import com.redescooter.ses.service.hub.source.consumer.service.base.HubConUserScooterService;
@Service
public class HubConUserScooterServiceImpl extends ServiceImpl<ConUserScooterMapper, HubConUserScooter> implements HubConUserScooterService {

    @Override
    public int updateBatch(List<HubConUserScooter> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<HubConUserScooter> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(HubConUserScooter record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(HubConUserScooter record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
