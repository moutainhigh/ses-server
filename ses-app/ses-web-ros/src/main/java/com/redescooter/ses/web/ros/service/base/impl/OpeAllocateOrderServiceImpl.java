package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeAllocateOrder;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeAllocateOrderMapper;
import com.redescooter.ses.web.ros.service.base.OpeAllocateOrderService;

@Service
public class OpeAllocateOrderServiceImpl extends ServiceImpl<OpeAllocateOrderMapper, OpeAllocateOrder> implements OpeAllocateOrderService {

    @Override
    public int updateBatch(List<OpeAllocateOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocateOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocateOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocateOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


