package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInvoiceOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceOrder;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoiceOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeInvoiceOrderServiceImpl extends ServiceImpl<OpeInvoiceOrderMapper, OpeInvoiceOrder> implements OpeInvoiceOrderService {

    @Override
    public int updateBatch(List<OpeInvoiceOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInvoiceOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInvoiceOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInvoiceOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
