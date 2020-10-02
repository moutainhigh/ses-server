package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeSysLogMapper;
import com.redescooter.ses.web.ros.dm.OpeSysLog;
import com.redescooter.ses.web.ros.service.base.OpeSysLogService;

@Service
public class OpeSysLogServiceImpl extends ServiceImpl<OpeSysLogMapper, OpeSysLog> implements OpeSysLogService {

    @Override
    public int updateBatch(List<OpeSysLog> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeSysLog> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeSysLog> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysLog record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysLog record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



