package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrder;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/22 9:50
 */
public interface OpeCombinOrderService extends IService<OpeCombinOrder> {

    int updateBatch(List<OpeCombinOrder> list);

    int updateBatchSelective(List<OpeCombinOrder> list);

    int batchInsert(List<OpeCombinOrder> list);

    int insertOrUpdate(OpeCombinOrder record);

    int insertOrUpdateSelective(OpeCombinOrder record);
}




