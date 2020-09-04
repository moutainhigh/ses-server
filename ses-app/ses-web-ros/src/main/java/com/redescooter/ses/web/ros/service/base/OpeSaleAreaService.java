package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSaleArea;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSaleAreaService extends IService<OpeSaleArea> {


    int updateBatch(List<OpeSaleArea> list);

    int updateBatchSelective(List<OpeSaleArea> list);

    int batchInsert(List<OpeSaleArea> list);

    int insertOrUpdate(OpeSaleArea record);

    int insertOrUpdateSelective(OpeSaleArea record);

}

