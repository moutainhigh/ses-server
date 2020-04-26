package com.redescooter.ses.mobile.rps.service.base.impl;

import com.redescooter.ses.mobile.rps.service.base.OpeStockPurchasService;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeStockPurchas;
import com.redescooter.ses.mobile.rps.dao.base.OpeStockPurchasMapper;

@Service
public class OpeStockPurchasServiceImpl extends ServiceImpl<OpeStockPurchasMapper, OpeStockPurchas> implements OpeStockPurchasService {

    @Override
    public int updateBatch(List<OpeStockPurchas> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeStockPurchas> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeStockPurchas record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeStockPurchas record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}








