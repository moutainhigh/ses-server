package com.redescooter.ses.service.hub.source.consumer.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.consumer.dao.HubConUserProfileMapper;
import com.redescooter.ses.service.hub.source.consumer.dm.HubConUserProfile;
import com.redescooter.ses.service.hub.source.consumer.service.base.HubConUserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 1:58 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@DS("consumer")
@Service
public class HubConUserProfileServiceImpl extends ServiceImpl<HubConUserProfileMapper, HubConUserProfile> implements HubConUserProfileService {

    @Override
    public int updateBatch(List<HubConUserProfile> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<HubConUserProfile> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(HubConUserProfile record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(HubConUserProfile record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
