package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeOutWhScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhScooterB;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhScooterBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OpeOutWhScooterBServiceImpl extends ServiceImpl<OpeOutWhScooterBMapper, OpeOutWhScooterB> implements OpeOutWhScooterBService {

    @Override
    public int updateBatch(List<OpeOutWhScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOutWhScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutWhScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutWhScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int updateBatchSelective(List<OpeOutWhScooterB> list) {
        return baseMapper.updateBatchSelective(list);
    }
}



