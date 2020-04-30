package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpePurchasLotTraceMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasLotTrace;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasLotTraceService;

@Service
public class OpePurchasLotTraceServiceImpl extends ServiceImpl<OpePurchasLotTraceMapper, OpePurchasLotTrace> implements OpePurchasLotTraceService {

    @Override
    public int updateBatch(List<OpePurchasLotTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpePurchasLotTrace> list) {
        return baseMapper.updateBatchSelective(list);
    }
}

