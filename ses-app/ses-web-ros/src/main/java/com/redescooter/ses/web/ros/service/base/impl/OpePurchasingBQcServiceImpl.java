package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePurchasingBQcMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasingBQc;
import com.redescooter.ses.web.ros.service.base.OpePurchasingBQcService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpePurchasingBQcServiceImpl extends ServiceImpl<OpePurchasingBQcMapper, OpePurchasingBQc> implements OpePurchasingBQcService {

    @Override
    public int updateBatch(List<OpePurchasingBQc> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasingBQc> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasingBQc record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasingBQc record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


