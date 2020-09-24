package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeProductionScooterBomDraftMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBomDraft;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomDraftService;

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


