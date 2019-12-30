package com.redescooter.ses.service.mobile.b.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dm.base.CorUserProfile;
import java.util.List;
import com.redescooter.ses.service.mobile.b.dao.base.CorUserProfileMapper;
import com.redescooter.ses.service.mobile.b.service.base.impl.CorUserProfileService;
@Service
public class CorUserProfileServiceImpl extends ServiceImpl<CorUserProfileMapper, CorUserProfile> implements CorUserProfileService{

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
