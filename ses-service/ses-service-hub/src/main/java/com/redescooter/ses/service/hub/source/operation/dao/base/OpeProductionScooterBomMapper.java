package com.redescooter.ses.service.hub.source.operation.dao.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionScooterBom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("operation")
public interface OpeProductionScooterBomMapper extends BaseMapper<OpeProductionScooterBom> {
    int updateBatch(List<OpeProductionScooterBom> list);

    int batchInsert(@Param("list") List<OpeProductionScooterBom> list);

    int insertOrUpdate(OpeProductionScooterBom record);

    int insertOrUpdateSelective(OpeProductionScooterBom record);
}