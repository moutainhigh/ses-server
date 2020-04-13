package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeSalesOrderMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesOrder;
import com.redescooter.ses.web.ros.service.base.OpeSalesOrderService;

@Service
public class OpeSalesOrderServiceImpl extends ServiceImpl<OpeSalesOrderMapper, OpeSalesOrder> implements OpeSalesOrderService {

    @Override
    public int updateBatch(List<OpeSalesOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSalesOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSalesOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSalesOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
