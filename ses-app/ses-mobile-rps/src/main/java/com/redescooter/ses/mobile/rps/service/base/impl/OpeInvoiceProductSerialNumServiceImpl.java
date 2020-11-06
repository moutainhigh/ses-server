package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceProductSerialNum;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeInvoiceProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoiceProductSerialNumService;

@Service
public class OpeInvoiceProductSerialNumServiceImpl extends ServiceImpl<OpeInvoiceProductSerialNumMapper, OpeInvoiceProductSerialNum> implements OpeInvoiceProductSerialNumService {

    @Override
    public int updateBatch(List<OpeInvoiceProductSerialNum> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInvoiceProductSerialNum> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInvoiceProductSerialNum record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInvoiceProductSerialNum record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}





