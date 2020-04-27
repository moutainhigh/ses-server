package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePurchasQcTrace;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchasQcTraceMapper;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasQcTraceService;

@Service
public class OpePurchasQcTraceServiceImpl extends ServiceImpl<OpePurchasQcTraceMapper, OpePurchasQcTrace> implements OpePurchasQcTraceService {

    @Override
    public int updateBatch(List<OpePurchasQcTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasQcTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasQcTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasQcTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


















