package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeProductionDeliveryDetail;
import com.redescooter.ses.web.ros.dao.base.OpeProductionDeliveryDetailMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionDeliveryDetailService;

@Service
public class OpeProductionDeliveryDetailServiceImpl extends ServiceImpl<OpeProductionDeliveryDetailMapper, OpeProductionDeliveryDetail> implements OpeProductionDeliveryDetailService {

    @Override
    public int updateBatch(List<OpeProductionDeliveryDetail> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionDeliveryDetail> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionDeliveryDetail record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionDeliveryDetail record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
