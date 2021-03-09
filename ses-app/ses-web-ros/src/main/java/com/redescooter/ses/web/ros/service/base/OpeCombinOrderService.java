package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeCombinOrder;

import java.util.List;

public interface OpeCombinOrderService extends IService<OpeCombinOrder> {


    int updateBatch(List<OpeCombinOrder> list);

    int batchInsert(List<OpeCombinOrder> list);

    int insertOrUpdate(OpeCombinOrder record);

    int insertOrUpdateSelective(OpeCombinOrder record);

}


