package com.redescooter.ses.mobile.rps.service.base.impl;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeCustomerService extends IService<OpeCustomer>{


    int updateBatch(List<OpeCustomer> list);

    int batchInsert(List<OpeCustomer> list);

    int insertOrUpdate(OpeCustomer record);

    int insertOrUpdateSelective(OpeCustomer record);

}
