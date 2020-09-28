package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.SellsyInvoiceTotal;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.SellsyInvoiceTotalMapper;
import com.redescooter.ses.web.ros.service.base.SellsyInvoiceTotalService;

@Service
public class SellsyInvoiceTotalServiceImpl extends ServiceImpl<SellsyInvoiceTotalMapper, SellsyInvoiceTotal> implements SellsyInvoiceTotalService {

    @Override
    public int updateBatch(List<SellsyInvoiceTotal> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SellsyInvoiceTotal> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SellsyInvoiceTotal record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SellsyInvoiceTotal record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}





