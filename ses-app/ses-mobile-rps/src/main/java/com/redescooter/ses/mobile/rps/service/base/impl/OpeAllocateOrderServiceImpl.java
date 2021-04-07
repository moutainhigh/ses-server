package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAllocateOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateOrder;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeAllocateOrderServiceImpl extends ServiceImpl<OpeAllocateOrderMapper, OpeAllocateOrder> implements OpeAllocateOrderService {

    @Override
    public int updateBatch(List<OpeAllocateOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocateOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocateOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocateOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
