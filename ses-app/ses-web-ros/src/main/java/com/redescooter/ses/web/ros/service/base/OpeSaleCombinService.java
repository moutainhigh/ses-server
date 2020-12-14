package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSaleCombin;

import java.util.List;

public interface OpeSaleCombinService extends IService<OpeSaleCombin> {


    int updateBatch(List<OpeSaleCombin> list);

    int batchInsert(List<OpeSaleCombin> list);

    int insertOrUpdate(OpeSaleCombin record);

    int insertOrUpdateSelective(OpeSaleCombin record);

}

