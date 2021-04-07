package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasPayment;

import java.util.List;

public interface OpePurchasPaymentService extends IService<OpePurchasPayment> {


    int updateBatch(List<OpePurchasPayment> list);

    int batchInsert(List<OpePurchasPayment> list);

    int insertOrUpdate(OpePurchasPayment record);

    int insertOrUpdateSelective(OpePurchasPayment record);

}


