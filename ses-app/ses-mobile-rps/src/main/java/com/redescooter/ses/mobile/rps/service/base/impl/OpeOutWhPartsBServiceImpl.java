package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeOutWhPartsBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhPartsB;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhPartsBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class OpeOutWhPartsBServiceImpl extends ServiceImpl<OpeOutWhPartsBMapper, OpeOutWhPartsB> implements OpeOutWhPartsBService {

    @Override
    public int updateBatch(List<OpeOutWhPartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOutWhPartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutWhPartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutWhPartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


