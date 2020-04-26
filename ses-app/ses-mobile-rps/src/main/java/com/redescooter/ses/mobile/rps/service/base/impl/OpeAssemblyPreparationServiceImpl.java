package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAssemblyPreparationMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyPreparation;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyPreparationService;
@Service
public class OpeAssemblyPreparationServiceImpl extends ServiceImpl<OpeAssemblyPreparationMapper, OpeAssemblyPreparation> implements OpeAssemblyPreparationService{

    @Override
    public int updateBatch(List<OpeAssemblyPreparation> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeAssemblyPreparation> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeAssemblyPreparation record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeAssemblyPreparation record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
