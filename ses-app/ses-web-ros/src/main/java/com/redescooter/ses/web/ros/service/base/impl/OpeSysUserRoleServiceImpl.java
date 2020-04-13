package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserRoleMapper;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;

@Service
public class OpeSysUserRoleServiceImpl extends ServiceImpl<OpeSysUserRoleMapper, OpeSysUserRole> implements OpeSysUserRoleService {

    @Override
    public int updateBatch(List<OpeSysUserRole> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSysUserRole> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysUserRole record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysUserRole record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


