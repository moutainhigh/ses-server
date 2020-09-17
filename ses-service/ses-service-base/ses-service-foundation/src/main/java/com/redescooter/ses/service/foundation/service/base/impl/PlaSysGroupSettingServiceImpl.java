package com.redescooter.ses.service.foundation.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dao.base.PlaSysGroupSettingMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaSysGroupSetting;
import com.redescooter.ses.service.foundation.service.base.PlaSysGroupSettingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaSysGroupSettingServiceImpl extends ServiceImpl<PlaSysGroupSettingMapper, PlaSysGroupSetting> implements PlaSysGroupSettingService {

    @Override
    public int updateBatch(List<PlaSysGroupSetting> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<PlaSysGroupSetting> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(PlaSysGroupSetting record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(PlaSysGroupSetting record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
