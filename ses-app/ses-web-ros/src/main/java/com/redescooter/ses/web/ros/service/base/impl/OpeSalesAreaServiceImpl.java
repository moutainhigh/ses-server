package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeSalesAreaMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesArea;
import com.redescooter.ses.web.ros.service.base.OpeSalesAreaService;

@Service
public class OpeSalesAreaServiceImpl extends ServiceImpl<OpeSalesAreaMapper, OpeSalesArea> implements OpeSalesAreaService {

    @Override
    public int updateBatch(List<OpeSalesArea> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSalesArea> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSalesArea record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSalesArea record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

