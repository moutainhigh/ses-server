package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeOutWhCombinBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhCombinB;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhCombinBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OpeOutWhCombinBServiceImpl extends ServiceImpl<OpeOutWhCombinBMapper, OpeOutWhCombinB> implements OpeOutWhCombinBService {

    @Override
    public int updateBatch(List<OpeOutWhCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOutWhCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutWhCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutWhCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

