package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeSalesOrderDetailMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesOrderDetail;
import com.redescooter.ses.web.ros.service.base.OpeSalesOrderDetailService;

@Service
public class OpeSalesOrderDetailServiceImpl extends ServiceImpl<OpeSalesOrderDetailMapper, OpeSalesOrderDetail> implements OpeSalesOrderDetailService {

    @Override
    public int updateBatch(List<OpeSalesOrderDetail> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSalesOrderDetail> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSalesOrderDetail record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSalesOrderDetail record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
