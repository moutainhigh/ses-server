package com.redescooter.ses.web.delivery.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.delivery.dao.base.CorUserProfileMapper;
import com.redescooter.ses.web.delivery.dm.CorUserProfile;
import com.redescooter.ses.web.delivery.service.base.CorUserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 8:32 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Service
public class CorUserProfileServiceImpl extends ServiceImpl<CorUserProfileMapper, CorUserProfile> implements CorUserProfileService {

    @Override
    public int updateBatch(List<CorUserProfile> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorUserProfile> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorUserProfile record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorUserProfile record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
