package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderStatusFlowMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOrderStatusFlow;
import com.redescooter.ses.mobile.rps.service.base.OpeOrderStatusFlowService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeOrderStatusFlowServiceImpl extends ServiceImpl<OpeOrderStatusFlowMapper, OpeOrderStatusFlow> implements OpeOrderStatusFlowService {

    @Override
    public int updateBatch(List<OpeOrderStatusFlow> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOrderStatusFlow> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOrderStatusFlow record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOrderStatusFlow record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
