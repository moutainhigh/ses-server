package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrderDetail;
import com.redescooter.ses.web.ros.dao.base.OpeSalesDeliveryOrderDetailMapper;

import java.util.List;

import com.redescooter.ses.web.ros.service.base.OpeSalesDeliveryOrderDetailService;

@Service
public class OpeSalesDeliveryOrderDetailServiceImpl extends ServiceImpl<OpeSalesDeliveryOrderDetailMapper, OpeSalesDeliveryOrderDetail> implements OpeSalesDeliveryOrderDetailService {

    @Override
    public int updateBatch(List<OpeSalesDeliveryOrderDetail> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSalesDeliveryOrderDetail> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSalesDeliveryOrderDetail record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSalesDeliveryOrderDetail record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
