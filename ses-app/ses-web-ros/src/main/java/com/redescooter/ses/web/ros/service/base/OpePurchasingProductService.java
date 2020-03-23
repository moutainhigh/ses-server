package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasingProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePurchasingProductService extends IService<OpePurchasingProduct> {


    int updateBatch(List<OpePurchasingProduct> list);

    int batchInsert(List<OpePurchasingProduct> list);

    int insertOrUpdate(OpePurchasingProduct record);

    int insertOrUpdateSelective(OpePurchasingProduct record);

}

