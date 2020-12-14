package com.redescooter.ses.service.hub.source.operation.service.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionScooterBom;

import java.util.List;

@DS("operation")
public interface OpeProductionScooterBomService extends IService<OpeProductionScooterBom> {

    int updateBatch(List<OpeProductionScooterBom> list);

    int batchInsert(List<OpeProductionScooterBom> list);

    int insertOrUpdate(OpeProductionScooterBom record);

    int insertOrUpdateSelective(OpeProductionScooterBom record);

}
