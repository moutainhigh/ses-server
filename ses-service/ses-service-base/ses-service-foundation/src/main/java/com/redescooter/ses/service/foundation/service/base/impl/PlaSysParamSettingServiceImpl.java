package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.service.foundation.dm.base.PlaSysParamSetting;
import com.redescooter.ses.service.foundation.dao.base.PlaSysParamSettingMapper;
import com.redescooter.ses.service.foundation.service.base.PlaSysParamSettingService;

@Service
public class PlaSysParamSettingServiceImpl extends ServiceImpl<PlaSysParamSettingMapper, PlaSysParamSetting> implements PlaSysParamSettingService {

    @Override
    public int updateBatch(List<PlaSysParamSetting> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<PlaSysParamSetting> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(PlaSysParamSetting record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(PlaSysParamSetting record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

