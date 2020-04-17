package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeAllocateBTraceMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateBTrace;
import com.redescooter.ses.web.ros.service.base.OpeAllocateBTraceService;
@Service
public class OpeAllocateBTraceServiceImpl extends ServiceImpl<OpeAllocateBTraceMapper, OpeAllocateBTrace> implements OpeAllocateBTraceService{

    @Override
    public int updateBatch(List<OpeAllocateBTrace> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeAllocateBTrace> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeAllocateBTrace record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeAllocateBTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
