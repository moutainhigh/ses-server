package com.redescooter.ses.service.hub.source.website.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.website.dao.SiteProductPriceMapper;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductPrice;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductPriceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("website")
public class SiteProductPriceServiceImpl extends ServiceImpl<SiteProductPriceMapper, SiteProductPrice> implements SiteProductPriceService {

    @Override
    public int updateBatch(List<SiteProductPrice> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SiteProductPrice> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SiteProductPrice> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SiteProductPrice record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SiteProductPrice record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


