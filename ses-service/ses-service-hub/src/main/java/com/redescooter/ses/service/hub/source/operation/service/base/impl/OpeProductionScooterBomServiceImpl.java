package com.redescooter.ses.service.hub.source.operation.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeProductionScooterBomMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionScooterBom;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeProductionScooterBomService;
import org.springframework.stereotype.Service;

import java.util.List;

@DS("operation")
@Service
public class OpeProductionScooterBomServiceImpl extends
    ServiceImpl<OpeProductionScooterBomMapper, OpeProductionScooterBom> implements OpeProductionScooterBomService {

    @Override
    public int updateBatch(List<OpeProductionScooterBom> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionScooterBom> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionScooterBom record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionScooterBom record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
