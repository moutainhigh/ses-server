package com.redescooter.ses.web.ros.service.impl.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeFactoryTraceMapper;
import com.redescooter.ses.web.ros.dm.OpeFactoryTrace;
import com.redescooter.ses.web.ros.service.base.OpeFactoryTraceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeFactoryTraceServiceImpl extends ServiceImpl<OpeFactoryTraceMapper, OpeFactoryTrace> implements OpeFactoryTraceService {

    @Override
    public int updateBatch(List<OpeFactoryTrace> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeFactoryTrace> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeFactoryTrace record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeFactoryTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
