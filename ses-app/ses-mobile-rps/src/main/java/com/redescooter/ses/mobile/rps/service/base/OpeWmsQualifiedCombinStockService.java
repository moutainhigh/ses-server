package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedCombinStock;
    /**
 *@author assert
 *@date 2021/1/14 15:44
 */
public interface OpeWmsQualifiedCombinStockService{


    int updateBatch(List<OpeWmsQualifiedCombinStock> list);

    int batchInsert(List<OpeWmsQualifiedCombinStock> list);

    int insertOrUpdate(OpeWmsQualifiedCombinStock record);

    int insertOrUpdateSelective(OpeWmsQualifiedCombinStock record);

}
