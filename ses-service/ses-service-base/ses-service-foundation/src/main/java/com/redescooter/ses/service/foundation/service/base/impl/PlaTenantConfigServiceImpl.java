package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantConfigMapper;
import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantConfig;
import com.redescooter.ses.service.foundation.service.base.PlaTenantConfigService;
@Service
public class PlaTenantConfigServiceImpl extends ServiceImpl<PlaTenantConfigMapper, PlaTenantConfig> implements PlaTenantConfigService{

    @Override
    public int updateBatch(List<PlaTenantConfig> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaTenantConfig> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaTenantConfig record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaTenantConfig record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
