package com.redescooter.ses.web.website.service.base;

import java.util.List;
import com.redescooter.ses.web.website.dm.SiteCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SiteCustomerService extends IService<SiteCustomer> {


    int updateBatch(List<SiteCustomer> list);

    int updateBatchSelective(List<SiteCustomer> list);

    int batchInsert(List<SiteCustomer> list);

    int insertOrUpdate(SiteCustomer record);

    int insertOrUpdateSelective(SiteCustomer record);

}


