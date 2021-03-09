package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;

import java.util.List;

public interface OpeProductionPartsService extends IService<OpeProductionParts> {

    int updateBatch(List<OpeProductionParts> list);

    int batchInsert(List<OpeProductionParts> list);

    int insertOrUpdate(OpeProductionParts record);

    int insertOrUpdateSelective(OpeProductionParts record);

}



