package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAllocateTraceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateTrace;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateTraceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeAllocateTraceServiceImpl extends ServiceImpl<OpeAllocateTraceMapper, OpeAllocateTrace> implements OpeAllocateTraceService {

    @Override
    public int updateBatch(List<OpeAllocateTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocateTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocateTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocateTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


