package com.redescooter.ses.service.hub.source.operation.service.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionCombinBom;

import java.util.List;

@DS("operation")
public interface OpeProductionCombinBomService extends IService<OpeProductionCombinBom> {

    int updateBatch(List<OpeProductionCombinBom> list);

    int batchInsert(List<OpeProductionCombinBom> list);

    int insertOrUpdate(OpeProductionCombinBom record);

    int insertOrUpdateSelective(OpeProductionCombinBom record);

}
