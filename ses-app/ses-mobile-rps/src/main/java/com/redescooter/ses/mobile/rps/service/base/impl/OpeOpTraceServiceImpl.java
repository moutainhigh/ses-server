package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeOpTraceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOpTrace;
import com.redescooter.ses.mobile.rps.service.base.OpeOpTraceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeOpTraceServiceImpl extends ServiceImpl<OpeOpTraceMapper, OpeOpTrace> implements OpeOpTraceService {

    @Override
    public int updateBatch(List<OpeOpTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOpTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOpTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOpTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
