package com.redescooter.ses.admin.dev.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.admin.dev.dao.base.AdmWorkOrderLogMapper;
import com.redescooter.ses.admin.dev.dm.AdmWorkOrderLog;
import com.redescooter.ses.admin.dev.service.base.AdmWorkOrderLogService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdmWorkOrderLogServiceImpl extends ServiceImpl<AdmWorkOrderLogMapper, AdmWorkOrderLog> implements AdmWorkOrderLogService{

    @Override
    public int updateBatch(List<AdmWorkOrderLog> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<AdmWorkOrderLog> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<AdmWorkOrderLog> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(AdmWorkOrderLog record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(AdmWorkOrderLog record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
