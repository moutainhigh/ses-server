package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionQualityTempate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionQualityTempateMapper extends BaseMapper<OpeProductionQualityTempate> {
    int updateBatch(List<OpeProductionQualityTempate> list);

    int batchInsert(@Param("list") List<OpeProductionQualityTempate> list);

    int insertOrUpdate(OpeProductionQualityTempate record);

    int insertOrUpdateSelective(OpeProductionQualityTempate record);
}