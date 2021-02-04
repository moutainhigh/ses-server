package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListCombinB;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 14:12
 */
public interface OpeCombinListCombinBService extends IService<OpeCombinListCombinB> {

    int updateBatch(List<OpeCombinListCombinB> list);

    int updateBatchSelective(List<OpeCombinListCombinB> list);

    int batchInsert(List<OpeCombinListCombinB> list);

    int insertOrUpdate(OpeCombinListCombinB record);

    int insertOrUpdateSelective(OpeCombinListCombinB record);
}



