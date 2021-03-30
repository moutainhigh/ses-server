package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchasePartsBMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasePartsB;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasePartsBService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpePurchasePartsBServiceImpl extends ServiceImpl<OpePurchasePartsBMapper, OpePurchasePartsB> implements OpePurchasePartsBService {

    @Override
    public int updateBatch(List<OpePurchasePartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasePartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasePartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasePartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
