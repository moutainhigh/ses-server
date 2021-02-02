package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockRecord;
    /**
 *@author assert
 *@date 2021/1/14 15:45
 */
public interface OpeWmsStockRecordService{


    int updateBatch(List<OpeWmsStockRecord> list);

    int batchInsert(List<OpeWmsStockRecord> list);

    int insertOrUpdate(OpeWmsStockRecord record);

    int insertOrUpdateSelective(OpeWmsStockRecord record);

}
