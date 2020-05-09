package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAssemblyBQcMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBQc;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyBQcService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeAssemblyBQcServiceImpl extends ServiceImpl<OpeAssemblyBQcMapper, OpeAssemblyBQc> implements OpeAssemblyBQcService {

    @Override
    public int updateBatch(List<OpeAssemblyBQc> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAssemblyBQc> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssemblyBQc record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssemblyBQc record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


