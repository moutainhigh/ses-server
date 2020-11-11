package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductionPurchasePartsB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionPurchasePartsBService extends IService<OpeProductionPurchasePartsB> {


    int updateBatch(List<OpeProductionPurchasePartsB> list);

    int batchInsert(List<OpeProductionPurchasePartsB> list);

    int insertOrUpdate(OpeProductionPurchasePartsB record);

    int insertOrUpdateSelective(OpeProductionPurchasePartsB record);

}

