package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedPartsStock;

/**
 * @author assert
 * @date 2021/1/14 15:44
 */
public interface OpeWmsQualifiedPartsStockService extends IService<OpeWmsQualifiedPartsStock> {


    int updateBatch(List<OpeWmsQualifiedPartsStock> list);

    int batchInsert(List<OpeWmsQualifiedPartsStock> list);

    int insertOrUpdate(OpeWmsQualifiedPartsStock record);

    int insertOrUpdateSelective(OpeWmsQualifiedPartsStock record);

}

