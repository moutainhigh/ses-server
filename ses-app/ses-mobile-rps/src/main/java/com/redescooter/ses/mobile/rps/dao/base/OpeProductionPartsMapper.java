package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionPartsMapper extends BaseMapper<OpeProductionParts> {
    int updateBatch(List<OpeProductionParts> list);

    int batchInsert(@Param("list") List<OpeProductionParts> list);

    int insertOrUpdate(OpeProductionParts record);

    int insertOrUpdateSelective(OpeProductionParts record);
}