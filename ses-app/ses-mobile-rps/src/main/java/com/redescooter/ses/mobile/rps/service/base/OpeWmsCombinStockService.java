package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;
    /**
 *@author assert
 *@date 2021/1/14 15:44
 */
public interface OpeWmsCombinStockService{


    int updateBatch(List<OpeWmsCombinStock> list);

    int batchInsert(List<OpeWmsCombinStock> list);

    int insertOrUpdate(OpeWmsCombinStock record);

    int insertOrUpdateSelective(OpeWmsCombinStock record);

}
