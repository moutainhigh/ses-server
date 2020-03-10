package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSysRoleMenuMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSysRoleMenuServiceImpl extends ServiceImpl<OpeSysRoleMenuMapper, OpeSysRoleMenu> implements OpeSysRoleMenuService {

    @Override
    public int updateBatch(List<OpeSysRoleMenu> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSysRoleMenu> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysRoleMenu record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysRoleMenu record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


