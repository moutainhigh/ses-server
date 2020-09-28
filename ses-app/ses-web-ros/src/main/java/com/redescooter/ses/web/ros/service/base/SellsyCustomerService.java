package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.SellsyCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SellsyCustomerService extends IService<SellsyCustomer> {


    int updateBatch(List<SellsyCustomer> list);

    int batchInsert(List<SellsyCustomer> list);

    int insertOrUpdate(SellsyCustomer record);

    int insertOrUpdateSelective(SellsyCustomer record);

}


