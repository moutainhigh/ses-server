package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionBom;

@Transactional
public interface OpeProductionBomService extends IService<OpeProductionBom> {

    int updateBatch(List<OpeProductionBom> list);

    int batchInsert(List<OpeProductionBom> list);

    int insertOrUpdate(OpeProductionBom record);

    int insertOrUpdateSelective(OpeProductionBom record);

}
