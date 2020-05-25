package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeTradePayRecord;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeTradePayRecordMapper extends BaseMapper<OpeTradePayRecord> {
    int updateBatch(List<OpeTradePayRecord> list);

    int batchInsert(@Param("list") List<OpeTradePayRecord> list);

    int insertOrUpdate(OpeTradePayRecord record);

    int insertOrUpdateSelective(OpeTradePayRecord record);
}