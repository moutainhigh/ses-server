package com.redescooter.ses.service.hub.source.operation.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeProductionCombinBomMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionCombinBom;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeProductionCombinBomService;
import org.springframework.stereotype.Service;

import java.util.List;

@DS("operation")
@Service
public class OpeProductionCombinBomServiceImpl extends ServiceImpl<OpeProductionCombinBomMapper, OpeProductionCombinBom>
    implements OpeProductionCombinBomService {

    @Override
    public int updateBatch(List<OpeProductionCombinBom> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionCombinBom> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionCombinBom record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionCombinBom record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
