package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInvoiceCombinDetailBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceCombinDetailB;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoiceCombinDetailBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OpeInvoiceCombinDetailBServiceImpl extends ServiceImpl<OpeInvoiceCombinDetailBMapper, OpeInvoiceCombinDetailB> implements OpeInvoiceCombinDetailBService {

    @Override
    public int updateBatch(List<OpeInvoiceCombinDetailB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInvoiceCombinDetailB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInvoiceCombinDetailB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInvoiceCombinDetailB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
