package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchasTraceMapper;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePurchasTrace;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasTraceService;

@Service
public class OpePurchasTraceServiceImpl extends ServiceImpl<OpePurchasTraceMapper, OpePurchasTrace> implements OpePurchasTraceService {

    @Override
    public int updateBatch(List<OpePurchasTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


