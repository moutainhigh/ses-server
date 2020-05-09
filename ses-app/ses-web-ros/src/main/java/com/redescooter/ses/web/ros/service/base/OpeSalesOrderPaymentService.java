package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSalesOrderPayment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeSalesOrderPaymentService extends IService<OpeSalesOrderPayment> {


    int updateBatch(List<OpeSalesOrderPayment> list);

    int batchInsert(List<OpeSalesOrderPayment> list);

    int insertOrUpdate(OpeSalesOrderPayment record);

    int insertOrUpdateSelective(OpeSalesOrderPayment record);

}
