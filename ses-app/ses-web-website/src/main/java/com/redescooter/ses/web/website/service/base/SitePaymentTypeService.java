package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SitePaymentType;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SitePaymentTypeService extends IService<SitePaymentType>{


    int updateBatch(List<SitePaymentType> list);

    int updateBatchSelective(List<SitePaymentType> list);

    int batchInsert(List<SitePaymentType> list);

    int insertOrUpdate(SitePaymentType record);

    int insertOrUpdateSelective(SitePaymentType record);

}
