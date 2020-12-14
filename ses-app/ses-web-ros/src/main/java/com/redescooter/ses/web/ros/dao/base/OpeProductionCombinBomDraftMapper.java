package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBomDraft;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionCombinBomDraftMapper extends BaseMapper<OpeProductionCombinBomDraft> {
    int updateBatch(List<OpeProductionCombinBomDraft> list);

    int batchInsert(@Param("list") List<OpeProductionCombinBomDraft> list);

    int insertOrUpdate(OpeProductionCombinBomDraft record);

    int insertOrUpdateSelective(OpeProductionCombinBomDraft record);
}