package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderCombinB;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 21:09
 */
public interface OpeCombinOrderCombinBService extends IService<OpeCombinOrderCombinB> {

    int updateBatch(List<OpeCombinOrderCombinB> list);

    int updateBatchSelective(List<OpeCombinOrderCombinB> list);

    int batchInsert(List<OpeCombinOrderCombinB> list);

    int insertOrUpdate(OpeCombinOrderCombinB record);

    int insertOrUpdateSelective(OpeCombinOrderCombinB record);
}


