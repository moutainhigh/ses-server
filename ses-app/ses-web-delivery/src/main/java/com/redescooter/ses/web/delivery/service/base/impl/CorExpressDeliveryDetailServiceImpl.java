package com.redescooter.ses.web.delivery.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.delivery.dm.CorExpressDeliveryDetail;
import com.redescooter.ses.web.delivery.dao.base.CorExpressDeliveryDetailMapper;
import com.redescooter.ses.web.delivery.service.base.CorExpressDeliveryDetailService;

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
