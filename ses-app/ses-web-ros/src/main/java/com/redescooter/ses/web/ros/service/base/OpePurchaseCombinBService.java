package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchaseCombinB;

import java.util.List;

public interface OpePurchaseCombinBService extends IService<OpePurchaseCombinB> {


    int updateBatch(List<OpePurchaseCombinB> list);

    int batchInsert(List<OpePurchaseCombinB> list);

    int insertOrUpdate(OpePurchaseCombinB record);

    int insertOrUpdateSelective(OpePurchaseCombinB record);

}
