package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;

import java.util.List;

public interface OpeProductionScooterBomService extends IService<OpeProductionScooterBom> {

    int updateBatch(List<OpeProductionScooterBom> list);

    int batchInsert(List<OpeProductionScooterBom> list);

    int insertOrUpdate(OpeProductionScooterBom record);

    int insertOrUpdateSelective(OpeProductionScooterBom record);

}




