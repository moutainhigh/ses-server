package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInvoiceScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceScooterB;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoiceScooterBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

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
