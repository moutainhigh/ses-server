package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeInvoiceScooterDetailBMapper;
import com.redescooter.ses.web.ros.dm.OpeInvoiceScooterDetailB;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceScooterDetailBService;

@Service
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
