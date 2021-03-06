package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePurchasPaymentMapper;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePurchasPayment;
import com.redescooter.ses.web.ros.service.base.OpePurchasPaymentService;

@Service
public class OpePurchasPaymentServiceImpl extends ServiceImpl<OpePurchasPaymentMapper, OpePurchasPayment> implements OpePurchasPaymentService {

    @Override
    public int updateBatch(List<OpePurchasPayment> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasPayment> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasPayment record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasPayment record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


