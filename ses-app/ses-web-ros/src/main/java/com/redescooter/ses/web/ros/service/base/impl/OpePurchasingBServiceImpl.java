package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpePurchasingBMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasingB;
import com.redescooter.ses.web.ros.service.base.OpePurchasingBService;

@Service
public class OpePurchasingBServiceImpl extends ServiceImpl<OpePurchasingBMapper, OpePurchasingB> implements OpePurchasingBService {

    @Override
    public int batchInsert(List<OpePurchasingB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasingB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasingB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

