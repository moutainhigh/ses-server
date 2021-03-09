package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchas;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpePurchasService extends IService<OpePurchas> {


    int updateBatch(List<OpePurchas> list);

    int batchInsert(List<OpePurchas> list);

    int insertOrUpdate(OpePurchas record);

    int insertOrUpdateSelective(OpePurchas record);

}













