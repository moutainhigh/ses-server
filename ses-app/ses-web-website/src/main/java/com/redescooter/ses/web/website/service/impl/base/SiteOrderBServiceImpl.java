package com.redescooter.ses.web.website.service.impl.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.website.dao.base.SiteOrderBMapper;
import com.redescooter.ses.web.website.dm.SiteOrderB;
import com.redescooter.ses.web.website.service.base.SiteOrderBService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SiteOrderBServiceImpl extends ServiceImpl<SiteOrderBMapper, SiteOrderB> implements SiteOrderBService{

    @Override
    public int updateBatch(List<SiteOrderB> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SiteOrderB> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SiteOrderB> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SiteOrderB record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SiteOrderB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
