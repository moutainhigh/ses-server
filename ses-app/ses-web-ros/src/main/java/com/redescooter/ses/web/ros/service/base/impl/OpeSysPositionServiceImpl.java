package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysPosition;
import com.redescooter.ses.web.ros.dao.base.OpeSysPositionMapper;
import com.redescooter.ses.web.ros.service.base.OpeSysPositionService;

@Service
public class OpeSysPositionServiceImpl extends ServiceImpl<OpeSysPositionMapper, OpeSysPosition> implements OpeSysPositionService {

    @Override
    public int updateBatch(List<OpeSysPosition> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeSysPosition> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeSysPosition> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysPosition record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysPosition record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

