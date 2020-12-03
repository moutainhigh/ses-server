package com.redescooter.ses.admin.dev.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.admin.dev.dao.base.AdmWorkOrderMapper;
import com.redescooter.ses.admin.dev.dm.AdmWorkOrder;
import com.redescooter.ses.admin.dev.service.base.AdmWorkOrderService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdmWorkOrderServiceImpl extends ServiceImpl<AdmWorkOrderMapper, AdmWorkOrder> implements AdmWorkOrderService{

    @Override
    public int updateBatch(List<AdmWorkOrder> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<AdmWorkOrder> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<AdmWorkOrder> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(AdmWorkOrder record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(AdmWorkOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
