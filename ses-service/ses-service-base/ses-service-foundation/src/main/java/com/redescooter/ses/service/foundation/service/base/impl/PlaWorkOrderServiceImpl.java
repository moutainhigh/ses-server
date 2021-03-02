package com.redescooter.ses.service.foundation.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dao.base.PlaWorkOrderMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaWorkOrder;
import com.redescooter.ses.service.foundation.service.base.PlaWorkOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaWorkOrderServiceImpl extends ServiceImpl<PlaWorkOrderMapper, PlaWorkOrder> implements PlaWorkOrderService {

    @Override
    public int updateBatch(List<PlaWorkOrder> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<PlaWorkOrder> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<PlaWorkOrder> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaWorkOrder record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaWorkOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
