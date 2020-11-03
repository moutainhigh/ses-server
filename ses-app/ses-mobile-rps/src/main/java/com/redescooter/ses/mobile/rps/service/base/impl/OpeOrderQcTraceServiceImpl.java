package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeOrderQcTrace;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderQcTraceMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeOrderQcTraceService;
@Service
public class OpeOrderQcTraceServiceImpl extends ServiceImpl<OpeOrderQcTraceMapper, OpeOrderQcTrace> implements OpeOrderQcTraceService{

    @Override
    public int updateBatch(List<OpeOrderQcTrace> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeOrderQcTrace> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeOrderQcTrace record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeOrderQcTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
