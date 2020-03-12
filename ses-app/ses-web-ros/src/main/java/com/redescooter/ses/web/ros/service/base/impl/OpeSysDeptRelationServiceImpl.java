package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;
import com.redescooter.ses.web.ros.dao.base.OpeSysDeptRelationMapper;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptRelationService;

@Service
public class OpeSysDeptRelationServiceImpl extends ServiceImpl<OpeSysDeptRelationMapper, OpeSysDeptRelation> implements OpeSysDeptRelationService {

    @Override
    public int batchInsert(List<OpeSysDeptRelation> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysDeptRelation record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysDeptRelation record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
