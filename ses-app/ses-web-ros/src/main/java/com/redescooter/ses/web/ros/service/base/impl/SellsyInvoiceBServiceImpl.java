package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.SellsyInvoiceBMapper;
import com.redescooter.ses.web.ros.dm.SellsyInvoiceB;
import com.redescooter.ses.web.ros.service.base.SellsyInvoiceBService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellsyInvoiceBServiceImpl extends ServiceImpl<SellsyInvoiceBMapper, SellsyInvoiceB> implements SellsyInvoiceBService {

    @Override
    public int updateBatch(List<SellsyInvoiceB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SellsyInvoiceB> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SellsyInvoiceB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SellsyInvoiceB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SellsyInvoiceB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
