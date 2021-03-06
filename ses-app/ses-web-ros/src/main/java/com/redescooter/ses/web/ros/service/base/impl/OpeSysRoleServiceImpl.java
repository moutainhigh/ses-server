package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSysRoleMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRole;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSysRoleServiceImpl extends ServiceImpl<OpeSysRoleMapper, OpeSysRole> implements OpeSysRoleService {

    @Override
    public int updateBatch(List<OpeSysRole> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSysRole> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysRole record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysRole record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int updateBatchSelective(List<OpeSysRole> list) {
        return 0;
    }
}


