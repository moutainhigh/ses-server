package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSalePartsMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleParts;
import com.redescooter.ses.web.ros.service.base.OpeSalePartsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSalePartsServiceImpl extends ServiceImpl<OpeSalePartsMapper, OpeSaleParts> implements OpeSalePartsService {

    @Override
    public int updateBatch(List<OpeSaleParts> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSaleParts> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSaleParts record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSaleParts record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

