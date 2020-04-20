package com.redescooter.ses.mobile.rps.service.base.impl;

import com.redescooter.ses.mobile.rps.dm.OpePurchasB;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchasBMapper;

import com.redescooter.ses.mobile.rps.service.base.OpePurchasBService;

@Service
public class OpePurchasBServiceImpl extends ServiceImpl<OpePurchasBMapper, OpePurchasB> implements OpePurchasBService {

    @Override
    public int batchInsert(List<OpePurchasB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}













