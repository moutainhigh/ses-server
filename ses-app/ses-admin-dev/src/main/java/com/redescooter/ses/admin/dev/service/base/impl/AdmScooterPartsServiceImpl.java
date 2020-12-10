package com.redescooter.ses.admin.dev.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.admin.dev.dao.base.AdmScooterPartsMapper;
import com.redescooter.ses.admin.dev.dm.AdmScooterParts;
import com.redescooter.ses.admin.dev.service.base.AdmScooterPartsService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdmScooterPartsServiceImpl extends ServiceImpl<AdmScooterPartsMapper, AdmScooterParts> implements AdmScooterPartsService{

    @Override
    public int updateBatch(List<AdmScooterParts> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<AdmScooterParts> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<AdmScooterParts> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(AdmScooterParts record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(AdmScooterParts record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
