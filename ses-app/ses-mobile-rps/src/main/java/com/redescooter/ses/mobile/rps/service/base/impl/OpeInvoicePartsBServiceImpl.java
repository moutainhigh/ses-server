package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInvoicePartsBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoicePartsB;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoicePartsBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OpeInvoicePartsBServiceImpl extends ServiceImpl<OpeInvoicePartsBMapper, OpeInvoicePartsB> implements OpeInvoicePartsBService {

    @Override
    public int updateBatch(List<OpeInvoicePartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInvoicePartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInvoicePartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInvoicePartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
