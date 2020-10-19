package com.redescooter.ses.mobile.rps.service.base.impl;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeProductionQualityTempate;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionQualityTempateService extends IService<OpeProductionQualityTempate> {

    int updateBatch(List<OpeProductionQualityTempate> list);

    int batchInsert(List<OpeProductionQualityTempate> list);

    int insertOrUpdate(OpeProductionQualityTempate record);

    int insertOrUpdateSelective(OpeProductionQualityTempate record);

}
