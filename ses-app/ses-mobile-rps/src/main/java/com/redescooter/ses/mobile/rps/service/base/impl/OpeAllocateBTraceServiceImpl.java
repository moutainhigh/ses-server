package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAllocateBTraceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateBTrace;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateBTraceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeAllocateBTraceServiceImpl extends ServiceImpl<OpeAllocateBTraceMapper, OpeAllocateBTrace> implements OpeAllocateBTraceService {

    @Override
    public int updateBatch(List<OpeAllocateBTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocateBTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocateBTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocateBTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



