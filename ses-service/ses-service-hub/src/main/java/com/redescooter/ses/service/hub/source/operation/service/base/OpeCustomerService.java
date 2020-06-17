package com.redescooter.ses.service.hub.source.operation.service.base;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

@DS("operation")
public interface OpeCustomerService extends IService<OpeCustomer> {


    int updateBatch(List<OpeCustomer> list);

    int batchInsert(List<OpeCustomer> list);

    int insertOrUpdate(OpeCustomer record);

    int insertOrUpdateSelective(OpeCustomer record);

}



