package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsDraftHistoryRecord;

import java.util.List;

public interface OpePartsDraftHistoryRecordService extends IService<OpePartsDraftHistoryRecord> {


    int updateBatch(List<OpePartsDraftHistoryRecord> list);

    int batchInsert(List<OpePartsDraftHistoryRecord> list);

    int insertOrUpdate(OpePartsDraftHistoryRecord record);

    int insertOrUpdateSelective(OpePartsDraftHistoryRecord record);

}

