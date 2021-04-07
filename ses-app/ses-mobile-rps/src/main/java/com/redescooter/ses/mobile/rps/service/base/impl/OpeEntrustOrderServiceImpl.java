package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeEntrustOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustOrder;
import com.redescooter.ses.mobile.rps.service.base.OpeEntrustOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeEntrustOrderServiceImpl extends ServiceImpl<OpeEntrustOrderMapper, OpeEntrustOrder> implements OpeEntrustOrderService {

    @Override
    public int updateBatch(List<OpeEntrustOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeEntrustOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeEntrustOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeEntrustOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
