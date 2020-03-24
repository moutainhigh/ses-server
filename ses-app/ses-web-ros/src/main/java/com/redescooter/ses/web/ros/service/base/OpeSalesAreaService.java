package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSalesArea;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSalesAreaService extends IService<OpeSalesArea> {


    int updateBatch(List<OpeSalesArea> list);

    int batchInsert(List<OpeSalesArea> list);

    int insertOrUpdate(OpeSalesArea record);

    int insertOrUpdateSelective(OpeSalesArea record);

}

