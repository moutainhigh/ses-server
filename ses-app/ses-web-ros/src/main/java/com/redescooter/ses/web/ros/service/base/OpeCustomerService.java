package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeCustomer;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeCustomerService extends IService<OpeCustomer> {


    int updateBatch(List<OpeCustomer> list);

    int batchInsert(List<OpeCustomer> list);

    int insertOrUpdate(OpeCustomer record);

    int insertOrUpdateSelective(OpeCustomer record);

}

