package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionCombinBomMapper extends BaseMapper<OpeProductionCombinBom> {
    int updateBatch(List<OpeProductionCombinBom> list);

    int batchInsert(@Param("list") List<OpeProductionCombinBom> list);

    int insertOrUpdate(OpeProductionCombinBom record);

    int insertOrUpdateSelective(OpeProductionCombinBom record);
}