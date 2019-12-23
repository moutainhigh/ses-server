package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dm.base.PlaSysSequence;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaSysSequenceMapper;
import com.redescooter.ses.service.foundation.service.base.PlaSysSequenceService;
@Service
public class PlaSysSequenceServiceImpl extends ServiceImpl<PlaSysSequenceMapper, PlaSysSequence> implements PlaSysSequenceService{

    @Override
    public int updateBatch(List<PlaSysSequence> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaSysSequence> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaSysSequence record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaSysSequence record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
