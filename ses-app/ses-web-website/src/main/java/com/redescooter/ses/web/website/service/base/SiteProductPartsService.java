package com.redescooter.ses.web.website.service.base;

import com.redescooter.ses.web.website.dm.SiteProductParts;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SiteProductPartsService extends IService<SiteProductParts> {


    int updateBatch(List<SiteProductParts> list);

    int updateBatchSelective(List<SiteProductParts> list);

    int batchInsert(List<SiteProductParts> list);

    int insertOrUpdate(SiteProductParts record);

    int insertOrUpdateSelective(SiteProductParts record);

}



