package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartsDraftHistoryRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpePartsDraftHistoryRecordMapper extends BaseMapper<OpePartsDraftHistoryRecord> {
    int updateBatch(List<OpePartsDraftHistoryRecord> list);

    int batchInsert(@Param("list") List<OpePartsDraftHistoryRecord> list);

    int insertOrUpdate(OpePartsDraftHistoryRecord record);

    int insertOrUpdateSelective(OpePartsDraftHistoryRecord record);
}