package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderCombinB;

import java.util.List;

public interface OpeCombinOrderCombinBService extends IService<OpeCombinOrderCombinB> {


    int updateBatch(List<OpeCombinOrderCombinB> list);

    int batchInsert(List<OpeCombinOrderCombinB> list);

    int insertOrUpdate(OpeCombinOrderCombinB record);

    int insertOrUpdateSelective(OpeCombinOrderCombinB record);

}


