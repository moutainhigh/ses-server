package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaAppVersionMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import com.redescooter.ses.service.foundation.service.base.PlaAppVersionService;
@Service
public class PlaAppVersionServiceImpl extends ServiceImpl<PlaAppVersionMapper, PlaAppVersion> implements PlaAppVersionService{

    @Override
    public int updateBatch(List<PlaAppVersion> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaAppVersion> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaAppVersion record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaAppVersion record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
