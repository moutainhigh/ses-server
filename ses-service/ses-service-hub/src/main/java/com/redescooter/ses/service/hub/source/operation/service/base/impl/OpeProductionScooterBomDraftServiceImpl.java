package com.redescooter.ses.service.hub.source.operation.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeProductionScooterBomDraftMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionScooterBomDraft;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeProductionScooterBomDraftService;
import org.springframework.stereotype.Service;

import java.util.List;

@DS("operation")
@Service
public class OpeProductionScooterBomDraftServiceImpl
    extends ServiceImpl<OpeProductionScooterBomDraftMapper, OpeProductionScooterBomDraft>
    implements OpeProductionScooterBomDraftService {

    @Override
    public int updateBatch(List<OpeProductionScooterBomDraft> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionScooterBomDraft> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionScooterBomDraft record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionScooterBomDraft record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
