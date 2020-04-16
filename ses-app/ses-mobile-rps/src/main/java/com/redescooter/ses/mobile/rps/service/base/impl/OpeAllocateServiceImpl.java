package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeAllocate;
import com.redescooter.ses.mobile.rps.dao.base.OpeAllocateMapper;

@Service
public class OpeAllocateServiceImpl extends ServiceImpl<OpeAllocateMapper, OpeAllocate> implements OpeAllocateService {

    @Override
    public int updateBatch(List<OpeAllocate> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocate> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocate record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocate record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

