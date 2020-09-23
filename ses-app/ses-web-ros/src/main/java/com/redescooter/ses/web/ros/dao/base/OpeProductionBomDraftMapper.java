package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionBomDraft;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeProductionBomDraftMapper extends BaseMapper<OpeProductionBomDraft> {
    int updateBatch(List<OpeProductionBomDraft> list);

    int batchInsert(@Param("list") List<OpeProductionBomDraft> list);

    int insertOrUpdate(OpeProductionBomDraft record);

    int insertOrUpdateSelective(OpeProductionBomDraft record);
}