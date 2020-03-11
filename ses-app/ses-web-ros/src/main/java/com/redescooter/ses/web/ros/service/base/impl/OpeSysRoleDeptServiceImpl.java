package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSysRoleDeptMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRoleDept;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSysRoleDeptServiceImpl extends ServiceImpl<OpeSysRoleDeptMapper, OpeSysRoleDept> implements OpeSysRoleDeptService {

    @Override
    public int updateBatch(List<OpeSysRoleDept> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeSysRoleDept> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeSysRoleDept record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeSysRoleDept record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
