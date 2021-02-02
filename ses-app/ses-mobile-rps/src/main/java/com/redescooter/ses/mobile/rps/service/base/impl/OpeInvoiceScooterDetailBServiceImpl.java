package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInvoiceScooterDetailBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceScooterDetailB;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoiceScooterDetailBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OpeInvoiceScooterDetailBServiceImpl extends ServiceImpl<OpeInvoiceScooterDetailBMapper, OpeInvoiceScooterDetailB> implements OpeInvoiceScooterDetailBService {

    @Override
    public int updateBatch(List<OpeInvoiceScooterDetailB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInvoiceScooterDetailB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInvoiceScooterDetailB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInvoiceScooterDetailB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
