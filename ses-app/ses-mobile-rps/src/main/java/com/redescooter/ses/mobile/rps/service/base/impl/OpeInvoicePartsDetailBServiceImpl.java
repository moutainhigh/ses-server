package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInvoicePartsDetailBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoicePartsDetailB;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoicePartsDetailBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OpeInvoicePartsDetailBServiceImpl extends ServiceImpl<OpeInvoicePartsDetailBMapper, OpeInvoicePartsDetailB> implements OpeInvoicePartsDetailBService {

    @Override
    public int updateBatch(List<OpeInvoicePartsDetailB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInvoicePartsDetailB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInvoicePartsDetailB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInvoicePartsDetailB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
