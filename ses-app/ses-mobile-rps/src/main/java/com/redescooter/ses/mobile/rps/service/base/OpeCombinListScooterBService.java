package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListScooterB;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 14:12
 */
public interface OpeCombinListScooterBService extends IService<OpeCombinListScooterB> {

    int updateBatch(List<OpeCombinListScooterB> list);

    int updateBatchSelective(List<OpeCombinListScooterB> list);

    int batchInsert(List<OpeCombinListScooterB> list);

    int insertOrUpdate(OpeCombinListScooterB record);

    int insertOrUpdateSelective(OpeCombinListScooterB record);
}



