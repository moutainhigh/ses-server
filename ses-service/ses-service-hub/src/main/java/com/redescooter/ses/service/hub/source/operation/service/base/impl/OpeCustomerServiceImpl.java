package com.redescooter.ses.service.hub.source.operation.service.base.impl;

import com.redescooter.ses.service.hub.source.operation.dao.base.OpeCustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomer;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCustomerService;

@Service
public class OpeCustomerServiceImpl extends ServiceImpl<OpeCustomerMapper, OpeCustomer> implements OpeCustomerService {

    @Override
    public int updateBatch(List<OpeCustomer> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeCustomer> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCustomer record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCustomer record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



