package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWmsPartsStock;
    /**
 *@author assert
 *@date 2021/1/14 15:44
 */
public interface OpeWmsPartsStockService{


    int updateBatch(List<OpeWmsPartsStock> list);

    int batchInsert(List<OpeWmsPartsStock> list);

    int insertOrUpdate(OpeWmsPartsStock record);

    int insertOrUpdateSelective(OpeWmsPartsStock record);

}
