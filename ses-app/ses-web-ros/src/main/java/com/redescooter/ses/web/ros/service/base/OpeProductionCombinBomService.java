package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeProductionCombinBomService extends IService<OpeProductionCombinBom> {

    int updateBatch(List<OpeProductionCombinBom> list);

    int batchInsert(List<OpeProductionCombinBom> list);

    int insertOrUpdate(OpeProductionCombinBom record);

    int insertOrUpdateSelective(OpeProductionCombinBom record);

}

