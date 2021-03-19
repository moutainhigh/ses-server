package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionPurchasePartsBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPurchasePartsB;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionPurchasePartsBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class OpeProductionPurchasePartsBServiceImpl extends ServiceImpl<OpeProductionPurchasePartsBMapper, OpeProductionPurchasePartsB> implements OpeProductionPurchasePartsBService {

    @Override
    public int updateBatch(List<OpeProductionPurchasePartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionPurchasePartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionPurchasePartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionPurchasePartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

