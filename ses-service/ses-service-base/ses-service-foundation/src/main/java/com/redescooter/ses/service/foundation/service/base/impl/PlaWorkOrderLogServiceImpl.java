package com.redescooter.ses.service.foundation.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dao.base.PlaWorkOrderLogMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaWorkOrderLog;
import com.redescooter.ses.service.foundation.service.base.PlaWorkOrderLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaWorkOrderLogServiceImpl extends ServiceImpl<PlaWorkOrderLogMapper, PlaWorkOrderLog> implements PlaWorkOrderLogService {

    @Override
    public int updateBatch(List<PlaWorkOrderLog> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<PlaWorkOrderLog> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<PlaWorkOrderLog> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaWorkOrderLog record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaWorkOrderLog record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
