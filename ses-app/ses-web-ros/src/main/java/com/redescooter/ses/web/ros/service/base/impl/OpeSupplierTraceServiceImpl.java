package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSupplierTrace;
import com.redescooter.ses.web.ros.dao.base.OpeSupplierTraceMapper;
import com.redescooter.ses.web.ros.service.base.OpeSupplierTraceService;

@Service
public class OpeSupplierTraceServiceImpl extends ServiceImpl<OpeSupplierTraceMapper, OpeSupplierTrace> implements OpeSupplierTraceService {

    @Override
    public int updateBatch(List<OpeSupplierTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSupplierTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSupplierTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSupplierTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

