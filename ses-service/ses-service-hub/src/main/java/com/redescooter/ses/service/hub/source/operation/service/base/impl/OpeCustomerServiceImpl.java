package com.redescooter.ses.service.hub.source.operation.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeCustomerMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomer;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("operation")
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



