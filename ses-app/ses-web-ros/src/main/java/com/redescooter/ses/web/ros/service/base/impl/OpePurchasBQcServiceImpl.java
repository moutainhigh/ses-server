package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePurchasBQcMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasBQc;
import com.redescooter.ses.web.ros.service.base.OpePurchasBQcService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpePurchasBQcServiceImpl extends ServiceImpl<OpePurchasBQcMapper, OpePurchasBQc> implements OpePurchasBQcService {

    @Override
    public int updateBatch(List<OpePurchasBQc> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePurchasBQc> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasBQc record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasBQc record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



