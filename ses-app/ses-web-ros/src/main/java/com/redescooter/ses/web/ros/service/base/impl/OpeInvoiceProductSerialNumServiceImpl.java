package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeInvoiceProductSerialNumMapper;
import com.redescooter.ses.web.ros.dm.OpeInvoiceProductSerialNum;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceProductSerialNumService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OpeInvoiceProductSerialNumServiceImpl extends ServiceImpl<OpeInvoiceProductSerialNumMapper, OpeInvoiceProductSerialNum> implements OpeInvoiceProductSerialNumService{

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
