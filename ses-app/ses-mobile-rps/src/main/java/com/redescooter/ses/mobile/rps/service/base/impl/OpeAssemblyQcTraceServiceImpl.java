package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeAssemblyQcTraceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyQcTrace;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyQcTraceService;

@Service
public class OpeAssemblyQcTraceServiceImpl extends ServiceImpl<OpeAssemblyQcTraceMapper, OpeAssemblyQcTrace> implements OpeAssemblyQcTraceService {

    @Override
    public int updateBatch(List<OpeAssemblyQcTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAssemblyQcTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssemblyQcTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssemblyQcTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

