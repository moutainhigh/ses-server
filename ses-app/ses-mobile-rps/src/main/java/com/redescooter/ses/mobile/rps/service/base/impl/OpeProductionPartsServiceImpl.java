package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionPartsMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionPartsService;

@Service
public class OpeProductionPartsServiceImpl extends ServiceImpl<OpeProductionPartsMapper, OpeProductionParts>
    implements OpeProductionPartsService {

    @Override
    public int updateBatch(List<OpeProductionParts> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionParts> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionParts record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionParts record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
