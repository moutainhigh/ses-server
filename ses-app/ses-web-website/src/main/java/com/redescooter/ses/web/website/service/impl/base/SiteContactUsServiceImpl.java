package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dm.SiteContactUs;
import com.redescooter.ses.web.website.dao.base.SiteContactUsMapper;
import com.redescooter.ses.web.website.service.base.SiteContactUsService;
@Service
public class SiteContactUsServiceImpl extends ServiceImpl<SiteContactUsMapper, SiteContactUs> implements SiteContactUsService{

    @Override
    public int updateBatch(List<SiteContactUs> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SiteContactUs> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SiteContactUs> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SiteContactUs record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SiteContactUs record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
