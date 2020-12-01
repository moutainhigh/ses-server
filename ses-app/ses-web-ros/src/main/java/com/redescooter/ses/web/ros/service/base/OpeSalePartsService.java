package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSaleParts;

import java.util.List;

public interface OpeSalePartsService extends IService<OpeSaleParts> {


    int updateBatch(List<OpeSaleParts> list);

    int batchInsert(List<OpeSaleParts> list);

    int insertOrUpdate(OpeSaleParts record);

    int insertOrUpdateSelective(OpeSaleParts record);

}

