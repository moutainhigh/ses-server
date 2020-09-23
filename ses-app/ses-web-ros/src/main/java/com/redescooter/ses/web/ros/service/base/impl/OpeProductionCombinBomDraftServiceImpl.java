package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeProductionCombinBomDraftMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBomDraft;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomDraftService;

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
