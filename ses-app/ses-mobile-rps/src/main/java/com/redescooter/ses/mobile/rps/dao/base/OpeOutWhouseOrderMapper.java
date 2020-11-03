package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeOutWhouseOrderMapper extends BaseMapper<OpeOutWhouseOrder> {
    int updateBatch(List<OpeOutWhouseOrder> list);

    int batchInsert(@Param("list") List<OpeOutWhouseOrder> list);

    int insertOrUpdate(OpeOutWhouseOrder record);

    int insertOrUpdateSelective(OpeOutWhouseOrder record);
}