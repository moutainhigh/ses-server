package com.redescooter.ses.service.hub.source.corporate.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.corporate.dao.CorUserProfileMapper;
import com.redescooter.ses.service.hub.source.corporate.dm.CorUserProfile;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorUserProfileService;
/**
 * @author      Mr.lijiating
 * @Date:       23/12/2019 11:20 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */
@DS("corporate")
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
