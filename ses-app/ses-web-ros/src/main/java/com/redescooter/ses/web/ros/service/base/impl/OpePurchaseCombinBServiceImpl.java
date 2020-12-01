package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpePurchaseCombinB;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpePurchaseCombinBMapper;
import com.redescooter.ses.web.ros.service.base.OpePurchaseCombinBService;

@Service
public class OpePurchaseCombinBServiceImpl extends ServiceImpl<OpePurchaseCombinBMapper, OpePurchaseCombinB> implements OpePurchaseCombinBService {

    @Override
    public int updateBatch(List<OpePurchaseCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchaseCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchaseCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchaseCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
