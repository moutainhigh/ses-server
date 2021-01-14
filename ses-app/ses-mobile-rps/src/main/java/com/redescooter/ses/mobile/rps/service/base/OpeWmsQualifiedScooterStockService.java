package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedScooterStock;
    /**
 *@author assert
 *@date 2021/1/14 15:44
 */
public interface OpeWmsQualifiedScooterStockService{


    int updateBatch(List<OpeWmsQualifiedScooterStock> list);

    int batchInsert(List<OpeWmsQualifiedScooterStock> list);

    int insertOrUpdate(OpeWmsQualifiedScooterStock record);

    int insertOrUpdateSelective(OpeWmsQualifiedScooterStock record);

}
