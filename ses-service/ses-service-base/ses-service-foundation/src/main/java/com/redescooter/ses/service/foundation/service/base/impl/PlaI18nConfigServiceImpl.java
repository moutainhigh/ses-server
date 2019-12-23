package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaI18nConfigMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaI18nConfig;
import com.redescooter.ses.service.foundation.service.base.PlaI18nConfigService;
@Service
public class PlaI18nConfigServiceImpl extends ServiceImpl<PlaI18nConfigMapper, PlaI18nConfig> implements PlaI18nConfigService{

    @Override
    public int updateBatch(List<PlaI18nConfig> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaI18nConfig> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaI18nConfig record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaI18nConfig record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
