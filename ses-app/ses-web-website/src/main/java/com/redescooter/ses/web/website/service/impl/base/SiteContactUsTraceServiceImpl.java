package com.redescooter.ses.web.website.service.impl.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.website.dao.base.SiteContactUsTraceMapper;
import com.redescooter.ses.web.website.dm.SiteContactUsTrace;
import com.redescooter.ses.web.website.service.base.SiteContactUsTraceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteContactUsTraceServiceImpl extends ServiceImpl<SiteContactUsTraceMapper, SiteContactUsTrace> implements SiteContactUsTraceService {

    @Override
    public int updateBatch(List<SiteContactUsTrace> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SiteContactUsTrace> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SiteContactUsTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SiteContactUsTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SiteContactUsTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

