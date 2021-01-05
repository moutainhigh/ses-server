package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteProductModel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SiteProductModelService extends IService<SiteProductModel> {


    int updateBatch(List<SiteProductModel> list);

    int updateBatchSelective(List<SiteProductModel> list);

    int batchInsert(List<SiteProductModel> list);

    int insertOrUpdate(SiteProductModel record);

    int insertOrUpdateSelective(SiteProductModel record);

}



