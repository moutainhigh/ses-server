package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeProductionQualityTempate;
import com.redescooter.ses.web.ros.dao.base.OpeProductionQualityTempateMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionQualityTempateService;

@Service
public class OpeProductionQualityTempateServiceImpl
    extends ServiceImpl<OpeProductionQualityTempateMapper, OpeProductionQualityTempate>
    implements OpeProductionQualityTempateService {

    @Override
    public int updateBatch(List<OpeProductionQualityTempate> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionQualityTempate> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionQualityTempate record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionQualityTempate record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

