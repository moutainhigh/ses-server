package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeOrderSerialBindMapper extends BaseMapper<OpeOrderSerialBind> {
    int updateBatch(List<OpeOrderSerialBind> list);

    int batchInsert(@Param("list") List<OpeOrderSerialBind> list);

    int insertOrUpdate(OpeOrderSerialBind record);

    int insertOrUpdateSelective(OpeOrderSerialBind record);
}