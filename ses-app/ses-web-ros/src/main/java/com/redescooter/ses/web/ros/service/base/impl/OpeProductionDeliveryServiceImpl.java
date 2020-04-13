package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeProductionDelivery;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeProductionDeliveryMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionDeliveryService;

@Service
public class OpeProductionDeliveryServiceImpl extends ServiceImpl<OpeProductionDeliveryMapper, OpeProductionDelivery> implements OpeProductionDeliveryService {

    @Override
    public int updateBatch(List<OpeProductionDelivery> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionDelivery> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionDelivery record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionDelivery record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
