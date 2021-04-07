package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAllocateScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateScooterB;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateScooterBService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeAllocateScooterBServiceImpl extends ServiceImpl<OpeAllocateScooterBMapper, OpeAllocateScooterB> implements OpeAllocateScooterBService {

    @Override
    public int updateBatch(List<OpeAllocateScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocateScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocateScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocateScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
