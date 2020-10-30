package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchaseCombinBMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchaseCombinB;
import com.redescooter.ses.mobile.rps.service.base.OpePurchaseCombinBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

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
