package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeInvoicePartsDetailBMapper;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeInvoicePartsDetailB;
import com.redescooter.ses.web.ros.service.base.OpeInvoicePartsDetailBService;

@Service
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
