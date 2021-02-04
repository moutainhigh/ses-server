package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionPartsRelationMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPartsRelation;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionPartsRelationService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 21:34
 */
@Service
public class OpeProductionPartsRelationServiceImpl extends ServiceImpl<OpeProductionPartsRelationMapper, OpeProductionPartsRelation>
        implements OpeProductionPartsRelationService {

    @Override
    public int updateBatch(List<OpeProductionPartsRelation> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeProductionPartsRelation> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeProductionPartsRelation> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionPartsRelation record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionPartsRelation record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


