package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeOutwhTrace;
import com.redescooter.ses.web.ros.dao.base.OpeOutwhTraceMapper;
import com.redescooter.ses.web.ros.service.base.OpeOutwhTraceService;

@Service
public class OpeOutwhTraceServiceImpl extends ServiceImpl<OpeOutwhTraceMapper, OpeOutwhTrace> implements OpeOutwhTraceService {

    @Override
    public int updateBatch(List<OpeOutwhTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOutwhTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutwhTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutwhTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

