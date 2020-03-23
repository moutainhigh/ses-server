package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasing;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePurchasingService extends IService<OpePurchasing> {


    int updateBatch(List<OpePurchasing> list);

    int batchInsert(List<OpePurchasing> list);

    int insertOrUpdate(OpePurchasing record);

    int insertOrUpdateSelective(OpePurchasing record);

}


