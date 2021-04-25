package com.redescooter.ses.service.hub.source.website.service.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductPrice;

import java.util.List;

@DS("website")
public interface SiteProductPriceService extends IService<SiteProductPrice> {


    int updateBatch(List<SiteProductPrice> list);

    int updateBatchSelective(List<SiteProductPrice> list);

    int batchInsert(List<SiteProductPrice> list);

    int insertOrUpdate(SiteProductPrice record);

    int insertOrUpdateSelective(SiteProductPrice record);

}


