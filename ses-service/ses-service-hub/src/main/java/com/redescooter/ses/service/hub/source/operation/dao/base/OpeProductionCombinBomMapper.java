package com.redescooter.ses.service.hub.source.operation.dao.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeProductionCombinBom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("operation")
public interface OpeProductionCombinBomMapper extends BaseMapper<OpeProductionCombinBom> {
    int updateBatch(List<OpeProductionCombinBom> list);

    int batchInsert(@Param("list") List<OpeProductionCombinBom> list);

    int insertOrUpdate(OpeProductionCombinBom record);

    int insertOrUpdateSelective(OpeProductionCombinBom record);
}