package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.SellsyInvoiceMapper;
import com.redescooter.ses.web.ros.dm.SellsyInvoice;
import com.redescooter.ses.web.ros.service.base.SellsyInvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellsyInvoiceServiceImpl extends ServiceImpl<SellsyInvoiceMapper, SellsyInvoice> implements SellsyInvoiceService {

    @Override
    public int updateBatch(List<SellsyInvoice> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SellsyInvoice> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SellsyInvoice> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SellsyInvoice record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SellsyInvoice record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
