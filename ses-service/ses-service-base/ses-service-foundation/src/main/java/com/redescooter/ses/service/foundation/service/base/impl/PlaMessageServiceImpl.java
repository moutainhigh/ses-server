package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaMessage;
import com.redescooter.ses.service.foundation.dao.base.PlaMessageMapper;
import com.redescooter.ses.service.foundation.service.base.PlaMessageService;

@Service
public class PlaMessageServiceImpl extends ServiceImpl<PlaMessageMapper, PlaMessage> implements PlaMessageService {

    @Override
    public int updateBatch(List<PlaMessage> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<PlaMessage> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(PlaMessage record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(PlaMessage record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertOrUpdateWithBLOBs(PlaMessage record) {
        return baseMapper.insertOrUpdateWithBLOBs(record);
    }

    @Override
    public int updateBatchSelective(List<PlaMessage> list) {
        return baseMapper.updateBatchSelective(list);
    }
}


