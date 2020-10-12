package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSaleScooterColor;

import java.util.List;
public interface OpeSaleScooterColorService extends IService<OpeSaleScooterColor> {


    int batchInsert(List<OpeSaleScooterColor> list);

    int insertOrUpdate(OpeSaleScooterColor record);

    int insertOrUpdateSelective(OpeSaleScooterColor record);

}
