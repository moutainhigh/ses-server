package com.redescooter.ses.service.mobile.b.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorExpressDeliveryDetailMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryDetail;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressDeliveryDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorExpressDeliveryDetailServiceImpl extends ServiceImpl<CorExpressDeliveryDetailMapper, CorExpressDeliveryDetail> implements CorExpressDeliveryDetailService {

    @Override
    public int updateBatch(List<CorExpressDeliveryDetail> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<CorExpressDeliveryDetail> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<CorExpressDeliveryDetail> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorExpressDeliveryDetail record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorExpressDeliveryDetail record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




