package com.redescooter.ses.service.hub.source.operation.dao.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionCombinBomDraft;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("operation")
public interface OpeProductionCombinBomDraftMapper extends BaseMapper<OpeProductionCombinBomDraft> {
    int updateBatch(List<OpeProductionCombinBomDraft> list);

    int batchInsert(@Param("list") List<OpeProductionCombinBomDraft> list);

    int insertOrUpdate(OpeProductionCombinBomDraft record);

    int insertOrUpdateSelective(OpeProductionCombinBomDraft record);
}