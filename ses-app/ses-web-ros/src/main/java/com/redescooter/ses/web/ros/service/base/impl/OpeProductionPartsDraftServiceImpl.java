package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsDraft;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPartsDraftMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsDraftService;

@Service
public class OpeProductionPartsDraftServiceImpl extends
    ServiceImpl<OpeProductionPartsDraftMapper, OpeProductionPartsDraft> implements OpeProductionPartsDraftService {

    @Override
    public int updateBatch(List<OpeProductionPartsDraft> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionPartsDraft> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionPartsDraft record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionPartsDraft record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
