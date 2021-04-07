package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsRelation;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpeProductionPartsRelationService extends IService<OpeProductionPartsRelation> {

    int updateBatch(List<OpeProductionPartsRelation> list);

    int batchInsert(List<OpeProductionPartsRelation> list);

    int insertOrUpdate(OpeProductionPartsRelation record);

    int insertOrUpdateSelective(OpeProductionPartsRelation record);

}

