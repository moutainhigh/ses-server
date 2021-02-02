package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListPartsSerialBind;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 17:37
 */
public interface OpeCombinListPartsSerialBindService extends IService<OpeCombinListPartsSerialBind> {

    int updateBatch(List<OpeCombinListPartsSerialBind> list);

    int updateBatchSelective(List<OpeCombinListPartsSerialBind> list);

    int batchInsert(List<OpeCombinListPartsSerialBind> list);

    int insertOrUpdate(OpeCombinListPartsSerialBind record);

    int insertOrUpdateSelective(OpeCombinListPartsSerialBind record);
}



