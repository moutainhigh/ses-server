package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInvoiceCombinBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceCombinB;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoiceCombinBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OpeInvoiceCombinBServiceImpl extends ServiceImpl<OpeInvoiceCombinBMapper, OpeInvoiceCombinB> implements OpeInvoiceCombinBService {

    @Override
    public int updateBatch(List<OpeInvoiceCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInvoiceCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInvoiceCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInvoiceCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
