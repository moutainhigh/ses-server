package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListRelationParts;

import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 17:36
 */
public interface OpeCombinListRelationPartsService extends IService<OpeCombinListRelationParts> {

    int updateBatch(List<OpeCombinListRelationParts> list);

    int updateBatchSelective(List<OpeCombinListRelationParts> list);

    int batchInsert(List<OpeCombinListRelationParts> list);

    int insertOrUpdate(OpeCombinListRelationParts record);

    int insertOrUpdateSelective(OpeCombinListRelationParts record);
}


