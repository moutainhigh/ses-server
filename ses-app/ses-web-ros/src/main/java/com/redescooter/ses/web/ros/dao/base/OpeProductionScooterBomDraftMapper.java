package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBomDraft;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionScooterBomDraftMapper extends BaseMapper<OpeProductionScooterBomDraft> {
    int updateBatch(List<OpeProductionScooterBomDraft> list);

    int batchInsert(@Param("list") List<OpeProductionScooterBomDraft> list);

    int insertOrUpdate(OpeProductionScooterBomDraft record);

    int insertOrUpdateSelective(OpeProductionScooterBomDraft record);
}