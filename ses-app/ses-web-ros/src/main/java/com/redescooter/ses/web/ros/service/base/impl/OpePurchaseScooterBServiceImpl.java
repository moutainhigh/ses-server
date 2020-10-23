package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpePurchaseScooterBMapper;
import com.redescooter.ses.web.ros.dm.OpePurchaseScooterB;
import com.redescooter.ses.web.ros.service.base.OpePurchaseScooterBService;

@Service
public class OpePurchaseScooterBServiceImpl extends ServiceImpl<OpePurchaseScooterBMapper, OpePurchaseScooterB> implements OpePurchaseScooterBService {

    @Override
    public int updateBatch(List<OpePurchaseScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchaseScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchaseScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchaseScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
