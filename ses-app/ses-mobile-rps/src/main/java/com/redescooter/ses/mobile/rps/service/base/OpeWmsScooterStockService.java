package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeWmsScooterStock;

/**
 * @author assert
 * @date 2021/1/14 15:44
 */
public interface OpeWmsScooterStockService extends IService<OpeWmsScooterStock> {

    int updateBatch(List<OpeWmsScooterStock> list);

    int updateBatchSelective(List<OpeWmsScooterStock> list);

    int batchInsert(List<OpeWmsScooterStock> list);

    int insertOrUpdate(OpeWmsScooterStock record);

    int insertOrUpdateSelective(OpeWmsScooterStock record);
}


