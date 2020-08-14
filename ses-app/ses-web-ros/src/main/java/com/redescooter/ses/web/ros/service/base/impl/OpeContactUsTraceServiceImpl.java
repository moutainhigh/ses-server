package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeContactUsTrace;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeContactUsTraceMapper;
import com.redescooter.ses.web.ros.service.base.OpeContactUsTraceService;

@Service
public class OpeContactUsTraceServiceImpl extends ServiceImpl<OpeContactUsTraceMapper, OpeContactUsTrace> implements OpeContactUsTraceService {

    @Override
    public int updateBatch(List<OpeContactUsTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeContactUsTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeContactUsTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeContactUsTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}





