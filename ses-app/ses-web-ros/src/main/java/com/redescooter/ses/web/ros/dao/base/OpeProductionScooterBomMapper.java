package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionScooterBomMapper extends BaseMapper<OpeProductionScooterBom> {
    int updateBatch(List<OpeProductionScooterBom> list);

    int batchInsert(@Param("list") List<OpeProductionScooterBom> list);

    int insertOrUpdate(OpeProductionScooterBom record);

    int insertOrUpdateSelective(OpeProductionScooterBom record);
}