package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSaleCombinMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleCombin;
import com.redescooter.ses.web.ros.service.base.OpeSaleCombinService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSaleCombinServiceImpl extends ServiceImpl<OpeSaleCombinMapper, OpeSaleCombin> implements OpeSaleCombinService {

    @Override
    public int updateBatch(List<OpeSaleCombin> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSaleCombin> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSaleCombin record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSaleCombin record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

