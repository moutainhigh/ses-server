package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionQualityTempateMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionQualityTempate;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionQualityTempateService;

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
