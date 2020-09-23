package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionBom;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionBomMapper extends BaseMapper<OpeProductionBom> {
    int updateBatch(List<OpeProductionBom> list);

    int batchInsert(@Param("list") List<OpeProductionBom> list);

    int insertOrUpdate(OpeProductionBom record);

    int insertOrUpdateSelective(OpeProductionBom record);
}