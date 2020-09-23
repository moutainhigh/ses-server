package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductionBomDraft;
import com.redescooter.ses.web.ros.dao.base.OpeProductionBomDraftMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionBomDraftService;

@Service
public class OpeProductionBomDraftServiceImpl extends ServiceImpl<OpeProductionBomDraftMapper, OpeProductionBomDraft>
    implements OpeProductionBomDraftService {

    @Override
    public int updateBatch(List<OpeProductionBomDraft> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionBomDraft> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionBomDraft record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionBomDraft record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
