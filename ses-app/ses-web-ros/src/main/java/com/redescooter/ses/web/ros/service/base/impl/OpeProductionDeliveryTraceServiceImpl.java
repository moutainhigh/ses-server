package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeProductionDeliveryTrace;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeProductionDeliveryTraceMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionDeliveryTraceService;

@Service
public class OpeProductionDeliveryTraceServiceImpl extends ServiceImpl<OpeProductionDeliveryTraceMapper, OpeProductionDeliveryTrace> implements OpeProductionDeliveryTraceService {

    @Override
    public int updateBatch(List<OpeProductionDeliveryTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionDeliveryTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionDeliveryTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionDeliveryTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
