package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaMailConfigMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;
import com.redescooter.ses.service.foundation.service.base.PlaMailConfigService;
@Service
public class PlaMailConfigServiceImpl extends ServiceImpl<PlaMailConfigMapper, PlaMailConfig> implements PlaMailConfigService{

    @Override
    public int updateBatch(List<PlaMailConfig> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaMailConfig> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaMailConfig record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaMailConfig record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
