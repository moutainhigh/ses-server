package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpeProductionCombinBomService extends IService<OpeProductionCombinBom> {

    int updateBatch(List<OpeProductionCombinBom> list);

    int batchInsert(List<OpeProductionCombinBom> list);

    int insertOrUpdate(OpeProductionCombinBom record);

    int insertOrUpdateSelective(OpeProductionCombinBom record);

}



