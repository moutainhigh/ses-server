package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPurchasePartsB;
import java.util.List;

public interface OpeProductionPurchasePartsBService extends IService<OpeProductionPurchasePartsB> {


    int updateBatch(List<OpeProductionPurchasePartsB> list);

    int batchInsert(List<OpeProductionPurchasePartsB> list);

    int insertOrUpdate(OpeProductionPurchasePartsB record);

    int insertOrUpdateSelective(OpeProductionPurchasePartsB record);

}

