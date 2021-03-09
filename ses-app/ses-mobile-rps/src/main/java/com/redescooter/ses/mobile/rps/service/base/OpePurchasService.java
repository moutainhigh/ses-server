package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePurchas;

import java.util.List;

public interface OpePurchasService extends IService<OpePurchas> {


    int updateBatch(List<OpePurchas> list);

    int batchInsert(List<OpePurchas> list);

    int insertOrUpdate(OpePurchas record);

    int insertOrUpdateSelective(OpePurchas record);
}








