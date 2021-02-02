package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPartsRelation;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 21:34
 */
public interface OpeProductionPartsRelationService extends IService<OpeProductionPartsRelation> {
    int updateBatch(List<OpeProductionPartsRelation> list);

    int updateBatchSelective(List<OpeProductionPartsRelation> list);

    int batchInsert(List<OpeProductionPartsRelation> list);

    int insertOrUpdate(OpeProductionPartsRelation record);

    int insertOrUpdateSelective(OpeProductionPartsRelation record);
}


