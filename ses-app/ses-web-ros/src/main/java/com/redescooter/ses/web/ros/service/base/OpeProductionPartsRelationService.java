package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsRelation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeProductionPartsRelationService extends IService<OpeProductionPartsRelation> {

    int updateBatch(List<OpeProductionPartsRelation> list);

    int batchInsert(List<OpeProductionPartsRelation> list);

    int insertOrUpdate(OpeProductionPartsRelation record);

    int insertOrUpdateSelective(OpeProductionPartsRelation record);

}
