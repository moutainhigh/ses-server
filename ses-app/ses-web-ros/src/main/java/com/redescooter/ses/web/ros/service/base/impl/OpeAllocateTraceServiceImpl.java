package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeAllocateTraceMapper;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeAllocateTrace;
import com.redescooter.ses.web.ros.service.base.OpeAllocateTraceService;

@Service
public class OpeAllocateTraceServiceImpl extends ServiceImpl<OpeAllocateTraceMapper, OpeAllocateTrace> implements OpeAllocateTraceService {

    @Override
    public int updateBatch(List<OpeAllocateTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAllocateTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAllocateTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAllocateTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

