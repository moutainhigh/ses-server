package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionQualityTempateB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionQualityTempateBMapper extends BaseMapper<OpeProductionQualityTempateB> {
    int updateBatch(List<OpeProductionQualityTempateB> list);

    int batchInsert(@Param("list") List<OpeProductionQualityTempateB> list);

    int insertOrUpdate(OpeProductionQualityTempateB record);

    int insertOrUpdateSelective(OpeProductionQualityTempateB record);
}