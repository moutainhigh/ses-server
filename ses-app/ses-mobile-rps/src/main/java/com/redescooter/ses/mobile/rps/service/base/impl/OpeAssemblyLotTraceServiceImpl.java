package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyLotTrace;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeAssemblyLotTraceMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyLotTraceService;

@Service
public class OpeAssemblyLotTraceServiceImpl extends ServiceImpl<OpeAssemblyLotTraceMapper, OpeAssemblyLotTrace> implements OpeAssemblyLotTraceService {

    @Override
    public int updateBatch(List<OpeAssemblyLotTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeAssemblyLotTrace> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeAssemblyLotTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssemblyLotTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssemblyLotTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

