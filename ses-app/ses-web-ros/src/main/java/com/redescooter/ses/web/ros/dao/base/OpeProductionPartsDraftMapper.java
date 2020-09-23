package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsDraft;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionPartsDraftMapper extends BaseMapper<OpeProductionPartsDraft> {
    int updateBatch(List<OpeProductionPartsDraft> list);

    int batchInsert(@Param("list") List<OpeProductionPartsDraft> list);

    int insertOrUpdate(OpeProductionPartsDraft record);

    int insertOrUpdateSelective(OpeProductionPartsDraft record);
}