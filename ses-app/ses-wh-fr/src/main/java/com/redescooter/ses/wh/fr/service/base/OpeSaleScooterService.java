package com.redescooter.ses.wh.fr.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.wh.fr.dm.OpeSaleScooter;

import java.util.List;

public interface OpeSaleScooterService extends IService<OpeSaleScooter> {


    int updateBatch(List<OpeSaleScooter> list);

    int batchInsert(List<OpeSaleScooter> list);

    int insertOrUpdate(OpeSaleScooter record);

    int insertOrUpdateSelective(OpeSaleScooter record);

}

