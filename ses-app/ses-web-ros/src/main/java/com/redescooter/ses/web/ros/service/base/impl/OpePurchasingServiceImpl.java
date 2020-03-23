package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePurchasingMapper;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpePurchasing;
import com.redescooter.ses.web.ros.service.base.OpePurchasingService;

@Service
public class OpePurchasingServiceImpl extends ServiceImpl<OpePurchasingMapper, OpePurchasing> implements OpePurchasingService {

    @Override
    public int updateBatch(List<OpePurchasing> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasing> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasing record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasing record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


