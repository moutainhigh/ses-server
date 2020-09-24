package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsRelation;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPartsRelationMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsRelationService;

@Service
public class OpeProductionPartsRelationServiceImpl
    extends ServiceImpl<OpeProductionPartsRelationMapper, OpeProductionPartsRelation>
    implements OpeProductionPartsRelationService {

    @Override
    public int updateBatch(List<OpeProductionPartsRelation> list) {
        return baseMapper.updateBatch(list);
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

