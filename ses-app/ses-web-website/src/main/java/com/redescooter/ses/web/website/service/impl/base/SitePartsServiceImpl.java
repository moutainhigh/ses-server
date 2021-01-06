package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.website.dm.SiteParts;
import java.util.List;
import com.redescooter.ses.web.website.dao.base.SitePartsMapper;
import com.redescooter.ses.web.website.service.base.SitePartsService;

@Service
public class SitePartsServiceImpl extends ServiceImpl<SitePartsMapper, SiteParts> implements SitePartsService {

    @Override
    public int updateBatch(List<SiteParts> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SiteParts> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SiteParts> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SiteParts record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SiteParts record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



