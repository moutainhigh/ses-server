package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrder;
import com.redescooter.ses.web.ros.dao.base.OpeSalesDeliveryOrderMapper;
import com.redescooter.ses.web.ros.service.base.OpeSalesDeliveryOrderService;

@Service
public class OpeSalesDeliveryOrderServiceImpl extends ServiceImpl<OpeSalesDeliveryOrderMapper, OpeSalesDeliveryOrder> implements OpeSalesDeliveryOrderService {

    @Override
    public int updateBatch(List<OpeSalesDeliveryOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSalesDeliveryOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSalesDeliveryOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSalesDeliveryOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
