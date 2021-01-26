package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpePurchaseCombinB;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePurchaseCombinBService extends IService<OpePurchaseCombinB> {


    int updateBatch(List<OpePurchaseCombinB> list);

    int batchInsert(List<OpePurchaseCombinB> list);

    int insertOrUpdate(OpePurchaseCombinB record);

    int insertOrUpdateSelective(OpePurchaseCombinB record);

}
