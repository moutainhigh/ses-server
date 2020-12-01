package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeInvoiceCombinBMapper;
import com.redescooter.ses.web.ros.dm.OpeInvoiceCombinB;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceCombinBService;

@Service
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
