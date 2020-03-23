package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasingPayment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePurchasingPaymentService extends IService<OpePurchasingPayment> {


    int updateBatch(List<OpePurchasingPayment> list);

    int batchInsert(List<OpePurchasingPayment> list);

    int insertOrUpdate(OpePurchasingPayment record);

    int insertOrUpdateSelective(OpePurchasingPayment record);

}

