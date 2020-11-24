package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeInvoiceScooterBMapper;
import com.redescooter.ses.web.ros.dm.OpeInvoiceScooterB;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceScooterBService;

@Service
public class OpeInvoiceScooterBServiceImpl extends ServiceImpl<OpeInvoiceScooterBMapper, OpeInvoiceScooterB> implements OpeInvoiceScooterBService {

    @Override
    public int updateBatch(List<OpeInvoiceScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInvoiceScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInvoiceScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInvoiceScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
