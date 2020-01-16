package com.redescooter.ses.web.delivery.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.redescooter.ses.web.delivery.dao.base.CorExpressOrderMapper;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderService;

@Service
public class CorExpressOrderServiceImpl extends ServiceImpl<CorExpressOrderMapper, CorExpressOrder> implements CorExpressOrderService {

    @Override
    public int updateBatch(List<CorExpressOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<CorExpressOrder> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<CorExpressOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorExpressOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorExpressOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
