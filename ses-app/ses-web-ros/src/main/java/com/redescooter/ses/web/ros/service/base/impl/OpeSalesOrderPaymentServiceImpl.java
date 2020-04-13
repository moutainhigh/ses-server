package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeSalesOrderPaymentMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesOrderPayment;
import com.redescooter.ses.web.ros.service.base.OpeSalesOrderPaymentService;

@Service
public class OpeSalesOrderPaymentServiceImpl extends ServiceImpl<OpeSalesOrderPaymentMapper, OpeSalesOrderPayment> implements OpeSalesOrderPaymentService {

    @Override
    public int updateBatch(List<OpeSalesOrderPayment> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSalesOrderPayment> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSalesOrderPayment record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSalesOrderPayment record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
