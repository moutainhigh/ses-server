package com.redescooter.ses.service.mobile.c.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.c.service.base.ConUserProfileService;
import com.redescooter.ses.service.mobile.c.dao.base.ConUserProfileMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ConUserProfileServiceImpl extends ServiceImpl<ConUserProfileMapper, ConUserProfile> implements ConUserProfileService {

    @Override
    public int updateBatch(List<ConUserProfile> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<ConUserProfile> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(ConUserProfile record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(ConUserProfile record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
