package com.redescooter.ses.mobile.rps.service.base.impl;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeProductionQualityTempateB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionQualityTempateBService extends IService<OpeProductionQualityTempateB> {

    int updateBatch(List<OpeProductionQualityTempateB> list);

    int batchInsert(List<OpeProductionQualityTempateB> list);

    int insertOrUpdate(OpeProductionQualityTempateB record);

    int insertOrUpdateSelective(OpeProductionQualityTempateB record);

}
