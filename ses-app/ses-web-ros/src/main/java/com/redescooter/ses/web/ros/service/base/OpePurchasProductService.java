package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePurchasProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePurchasProductService extends IService<OpePurchasProduct>{


    int updateBatch(List<OpePurchasProduct> list);

    int batchInsert(List<OpePurchasProduct> list);

    int insertOrUpdate(OpePurchasProduct record);

    int insertOrUpdateSelective(OpePurchasProduct record);

}
