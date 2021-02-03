package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SitePaymentRecords;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SitePaymentRecordsService extends IService<SitePaymentRecords>{


    int updateBatch(List<SitePaymentRecords> list);

    int updateBatchSelective(List<SitePaymentRecords> list);

    int batchInsert(List<SitePaymentRecords> list);

    int insertOrUpdate(SitePaymentRecords record);

    int insertOrUpdateSelective(SitePaymentRecords record);

}
