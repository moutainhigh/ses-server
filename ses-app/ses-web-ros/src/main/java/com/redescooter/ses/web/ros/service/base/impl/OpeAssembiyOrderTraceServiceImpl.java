package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeAssembiyOrderTraceMapper;
import com.redescooter.ses.web.ros.dm.OpeAssembiyOrderTrace;
import com.redescooter.ses.web.ros.service.base.OpeAssembiyOrderTraceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeAssembiyOrderTraceServiceImpl extends ServiceImpl<OpeAssembiyOrderTraceMapper, OpeAssembiyOrderTrace> implements OpeAssembiyOrderTraceService {

    @Override
    public int updateBatch(List<OpeAssembiyOrderTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAssembiyOrderTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssembiyOrderTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssembiyOrderTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




