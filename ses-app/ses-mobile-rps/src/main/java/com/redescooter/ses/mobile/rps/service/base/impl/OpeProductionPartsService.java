package com.redescooter.ses.mobile.rps.service.base.impl;

import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionPartsService extends IService<OpeProductionParts> {

    int updateBatch(List<OpeProductionParts> list);

    int batchInsert(List<OpeProductionParts> list);

    int insertOrUpdate(OpeProductionParts record);

    int insertOrUpdateSelective(OpeProductionParts record);

}
