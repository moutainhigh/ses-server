package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeInvoiceOrderMapper;
import com.redescooter.ses.web.ros.dm.OpeInvoiceOrder;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceOrderService;

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





