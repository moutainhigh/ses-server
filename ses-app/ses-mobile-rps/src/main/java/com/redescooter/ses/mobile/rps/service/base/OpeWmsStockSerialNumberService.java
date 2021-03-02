package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockSerialNumber;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/22 20:10
 */
public interface OpeWmsStockSerialNumberService extends IService<OpeWmsStockSerialNumber> {

    int updateBatch(List<OpeWmsStockSerialNumber> list);

    int updateBatchSelective(List<OpeWmsStockSerialNumber> list);

    int batchInsert(List<OpeWmsStockSerialNumber> list);

    int insertOrUpdate(OpeWmsStockSerialNumber record);

    int insertOrUpdateSelective(OpeWmsStockSerialNumber record);
}



