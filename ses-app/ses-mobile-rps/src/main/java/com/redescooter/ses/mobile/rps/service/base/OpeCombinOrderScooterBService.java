package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderScooterB;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 21:09
 */
public interface OpeCombinOrderScooterBService extends IService<OpeCombinOrderScooterB> {

    int updateBatch(List<OpeCombinOrderScooterB> list);

    int updateBatchSelective(List<OpeCombinOrderScooterB> list);

    int batchInsert(List<OpeCombinOrderScooterB> list);

    int insertOrUpdate(OpeCombinOrderScooterB record);

    int insertOrUpdateSelective(OpeCombinOrderScooterB record);
}


