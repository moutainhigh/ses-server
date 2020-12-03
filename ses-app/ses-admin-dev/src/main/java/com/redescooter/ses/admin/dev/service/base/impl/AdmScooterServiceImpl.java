package com.redescooter.ses.admin.dev.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.admin.dev.dao.base.AdmScooterMapper;
import com.redescooter.ses.admin.dev.dm.AdmScooter;
import com.redescooter.ses.admin.dev.service.base.AdmScooterService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdmScooterServiceImpl extends ServiceImpl<AdmScooterMapper, AdmScooter> implements AdmScooterService{

    @Override
    public int updateBatch(List<AdmScooter> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<AdmScooter> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<AdmScooter> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(AdmScooter record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(AdmScooter record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
