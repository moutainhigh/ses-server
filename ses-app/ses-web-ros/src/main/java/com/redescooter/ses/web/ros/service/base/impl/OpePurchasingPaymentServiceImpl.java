package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpePurchasingPaymentMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasingPayment;
import com.redescooter.ses.web.ros.service.base.OpePurchasingPaymentService;

@Service
public class OpePurchasingPaymentServiceImpl extends ServiceImpl<OpePurchasingPaymentMapper, OpePurchasingPayment> implements OpePurchasingPaymentService {

    @Override
    public int updateBatch(List<OpePurchasingPayment> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasingPayment> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasingPayment record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasingPayment record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

