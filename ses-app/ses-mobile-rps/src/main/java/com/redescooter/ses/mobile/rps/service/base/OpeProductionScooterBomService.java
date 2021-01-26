package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeProductionScooterBom;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeProductionScooterBomService extends IService<OpeProductionScooterBom>{


    int updateBatch(List<OpeProductionScooterBom> list);

    int batchInsert(List<OpeProductionScooterBom> list);

    int insertOrUpdate(OpeProductionScooterBom record);

    int insertOrUpdateSelective(OpeProductionScooterBom record);

}
