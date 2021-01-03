package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SitePartsPrice;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SitePartsPriceService extends IService<SitePartsPrice>{


    int updateBatch(List<SitePartsPrice> list);

    int updateBatchSelective(List<SitePartsPrice> list);

    int batchInsert(List<SitePartsPrice> list);

    int insertOrUpdate(SitePartsPrice record);

    int insertOrUpdateSelective(SitePartsPrice record);

}
