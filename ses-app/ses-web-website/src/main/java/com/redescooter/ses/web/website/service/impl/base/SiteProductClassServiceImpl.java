package com.redescooter.ses.web.website.service.impl.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.website.dao.base.SiteProductClassMapper;
import com.redescooter.ses.web.website.dm.SiteProductClass;
import com.redescooter.ses.web.website.service.base.SiteProductClassService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SiteProductClassServiceImpl extends ServiceImpl<SiteProductClassMapper, SiteProductClass> implements SiteProductClassService{

    @Override
    public int updateBatch(List<SiteProductClass> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SiteProductClass> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SiteProductClass> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SiteProductClass record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SiteProductClass record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
