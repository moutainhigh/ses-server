package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartsHistoryRecord;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartsHistoryRecordMapper extends BaseMapper<OpePartsHistoryRecord> {
    int updateBatch(List<OpePartsHistoryRecord> list);

    int batchInsert(@Param("list") List<OpePartsHistoryRecord> list);

    int insertOrUpdate(OpePartsHistoryRecord record);

    int insertOrUpdateSelective(OpePartsHistoryRecord record);
}