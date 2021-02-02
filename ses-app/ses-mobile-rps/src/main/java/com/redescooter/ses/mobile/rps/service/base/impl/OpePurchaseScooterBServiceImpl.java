package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchaseScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchaseScooterB;
import com.redescooter.ses.mobile.rps.service.base.OpePurchaseScooterBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

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
