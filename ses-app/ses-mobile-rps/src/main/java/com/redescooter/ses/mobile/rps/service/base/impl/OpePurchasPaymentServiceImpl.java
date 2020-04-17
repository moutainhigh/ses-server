package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchasPaymentMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasPayment;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasPaymentService;
@Service
public class OpePurchasPaymentServiceImpl extends ServiceImpl<OpePurchasPaymentMapper, OpePurchasPayment> implements OpePurchasPaymentService{

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
