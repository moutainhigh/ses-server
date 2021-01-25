package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.website.dao.base.SiteProductPriceMapper;
import com.redescooter.ses.web.website.dm.SiteProductPrice;
import java.util.List;
import com.redescooter.ses.web.website.service.base.SiteProductPriceService;

@Service
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


