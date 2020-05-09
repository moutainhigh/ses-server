package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeWarehouseMapper;
import com.redescooter.ses.web.ros.dm.OpeWarehouse;
import com.redescooter.ses.web.ros.service.base.OpeWarehouseService;

@Service
public class OpeWarehouseServiceImpl extends ServiceImpl<OpeWarehouseMapper, OpeWarehouse> implements OpeWarehouseService {

    @Override
    public int updateBatch(List<OpeWarehouse> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeWarehouse> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeWarehouse record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeWarehouse record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
