package com.redescooter.ses.web.website.service.base;

import com.redescooter.ses.web.website.dm.SiteProductPrice;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SiteProductPriceService extends IService<SiteProductPrice> {


    int updateBatch(List<SiteProductPrice> list);

    int updateBatchSelective(List<SiteProductPrice> list);

    int batchInsert(List<SiteProductPrice> list);

    int insertOrUpdate(SiteProductPrice record);

    int insertOrUpdateSelective(SiteProductPrice record);

}

