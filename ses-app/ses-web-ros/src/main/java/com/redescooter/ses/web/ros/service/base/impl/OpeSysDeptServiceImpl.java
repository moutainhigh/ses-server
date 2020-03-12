package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSysDeptMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSysDeptServiceImpl extends ServiceImpl<OpeSysDeptMapper, OpeSysDept> implements OpeSysDeptService {

    @Override
    public int updateBatch(List<OpeSysDept> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSysDept> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysDept record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysDept record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


