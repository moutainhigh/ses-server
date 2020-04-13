package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserProfileMapper;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;

@Service
public class OpeSysUserProfileServiceImpl extends ServiceImpl<OpeSysUserProfileMapper, OpeSysUserProfile> implements OpeSysUserProfileService {

    @Override
    public int updateBatch(List<OpeSysUserProfile> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSysUserProfile> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysUserProfile record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysUserProfile record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

