package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteProduct;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SiteProductService extends IService<SiteProduct>{


    int updateBatch(List<SiteProduct> list);

    int updateBatchSelective(List<SiteProduct> list);

    int batchInsert(List<SiteProduct> list);

    int insertOrUpdate(SiteProduct record);

    int insertOrUpdateSelective(SiteProduct record);

}
