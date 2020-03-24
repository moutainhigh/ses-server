package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSalesOrderPayment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSalesOrderPaymentService extends IService<OpeSalesOrderPayment> {


    int updateBatch(List<OpeSalesOrderPayment> list);

    int batchInsert(List<OpeSalesOrderPayment> list);

    int insertOrUpdate(OpeSalesOrderPayment record);

    int insertOrUpdateSelective(OpeSalesOrderPayment record);

}
