package com.redescooter.ses.service.hub.service.corporate.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.service.hub.dao.corporate.base.CorUserProfileMapper;
import com.redescooter.ses.service.hub.dm.corporate.base.CorUserProfile;
import com.redescooter.ses.service.hub.service.corporate.base.CorUserProfileService;
@Service
public class CorUserProfileServiceImpl implements CorUserProfileService{

    @Resource
    private CorUserProfileMapper corUserProfileMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return corUserProfileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CorUserProfile record) {
        return corUserProfileMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(CorUserProfile record) {
        return corUserProfileMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorUserProfile record) {
        return corUserProfileMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(CorUserProfile record) {
        return corUserProfileMapper.insertSelective(record);
    }

    @Override
    public CorUserProfile selectByPrimaryKey(Long id) {
        return corUserProfileMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CorUserProfile record) {
        return corUserProfileMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CorUserProfile record) {
        return corUserProfileMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<CorUserProfile> list) {
        return corUserProfileMapper.updateBatch(list);
    }
}
