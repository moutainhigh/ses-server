package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePurchasPayment;

import java.util.List;

public interface OpePurchasPaymentService extends IService<OpePurchasPayment>{


    int updateBatch(List<OpePurchasPayment> list);

    int batchInsert(List<OpePurchasPayment> list);

    int insertOrUpdate(OpePurchasPayment record);

    int insertOrUpdateSelective(OpePurchasPayment record);

}
