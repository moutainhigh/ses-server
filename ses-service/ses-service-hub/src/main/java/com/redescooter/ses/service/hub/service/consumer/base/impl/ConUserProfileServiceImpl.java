package com.redescooter.ses.service.hub.service.consumer.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.service.hub.dao.customer.base.ConUserProfileMapper;
import com.redescooter.ses.service.hub.dm.customer.base.ConUserProfile;
import com.redescooter.ses.service.hub.service.consumer.base.ConUserProfileService;
@Service
public class ConUserProfileServiceImpl implements ConUserProfileService{

    @Resource
    private ConUserProfileMapper conUserProfileMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return conUserProfileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ConUserProfile record) {
        return conUserProfileMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(ConUserProfile record) {
        return conUserProfileMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(ConUserProfile record) {
        return conUserProfileMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(ConUserProfile record) {
        return conUserProfileMapper.insertSelective(record);
    }

    @Override
    public ConUserProfile selectByPrimaryKey(Long id) {
        return conUserProfileMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ConUserProfile record) {
        return conUserProfileMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ConUserProfile record) {
        return conUserProfileMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<ConUserProfile> list) {
        return conUserProfileMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<ConUserProfile> list) {
        return conUserProfileMapper.batchInsert(list);
    }

}
