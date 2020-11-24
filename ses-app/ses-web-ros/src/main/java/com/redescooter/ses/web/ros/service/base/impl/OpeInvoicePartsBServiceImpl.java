package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeInvoicePartsB;
import com.redescooter.ses.web.ros.dao.base.OpeInvoicePartsBMapper;
import com.redescooter.ses.web.ros.service.base.OpeInvoicePartsBService;

@Service
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
