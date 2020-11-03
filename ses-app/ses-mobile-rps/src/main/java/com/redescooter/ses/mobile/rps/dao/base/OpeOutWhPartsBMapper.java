package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhPartsB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeOutWhPartsBMapper extends BaseMapper<OpeOutWhPartsB> {
    int updateBatch(List<OpeOutWhPartsB> list);

    int batchInsert(@Param("list") List<OpeOutWhPartsB> list);

    int insertOrUpdate(OpeOutWhPartsB record);

    int insertOrUpdateSelective(OpeOutWhPartsB record);
}