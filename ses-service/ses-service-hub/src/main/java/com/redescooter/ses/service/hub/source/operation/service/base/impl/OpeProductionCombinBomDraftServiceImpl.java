package com.redescooter.ses.service.hub.source.operation.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeProductionCombinBomDraftMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionCombinBomDraft;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeProductionCombinBomDraftService;
import org.springframework.stereotype.Service;

import java.util.List;

@DS("operation")
@Service
public class OpeProductionCombinBomDraftServiceImpl
    extends ServiceImpl<OpeProductionCombinBomDraftMapper, OpeProductionCombinBomDraft>
    implements OpeProductionCombinBomDraftService {

    @Override
    public int updateBatch(List<OpeProductionCombinBomDraft> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionCombinBomDraft> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionCombinBomDraft record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionCombinBomDraft record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
