package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeCombinOrderCombinB;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeCombinOrderCombinBService extends IService<OpeCombinOrderCombinB>{


    int updateBatch(List<OpeCombinOrderCombinB> list);

    int batchInsert(List<OpeCombinOrderCombinB> list);

    int insertOrUpdate(OpeCombinOrderCombinB record);

    int insertOrUpdateSelective(OpeCombinOrderCombinB record);

}
