package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeAssemblyOrderPaymentMapper;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrderPayment;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderPaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeAssemblyOrderPaymentServiceImpl extends ServiceImpl<OpeAssemblyOrderPaymentMapper, OpeAssemblyOrderPayment> implements OpeAssemblyOrderPaymentService {

    @Override
    public int updateBatch(List<OpeAssemblyOrderPayment> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAssemblyOrderPayment> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssemblyOrderPayment record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssemblyOrderPayment record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


