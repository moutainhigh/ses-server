package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePurchasBQcTraceMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasBQcTrace;
import com.redescooter.ses.web.ros.service.base.OpePurchasBQcTraceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpePurchasBQcTraceServiceImpl extends ServiceImpl<OpePurchasBQcTraceMapper, OpePurchasBQcTrace> implements OpePurchasBQcTraceService {

    @Override
    public int updateBatch(List<OpePurchasBQcTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasBQcTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasBQcTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasBQcTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

