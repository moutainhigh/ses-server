package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePartsDraftHistoryRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePartsDraftHistoryRecordService extends IService<OpePartsDraftHistoryRecord>{


    int updateBatch(List<OpePartsDraftHistoryRecord> list);

    int batchInsert(List<OpePartsDraftHistoryRecord> list);

    int insertOrUpdate(OpePartsDraftHistoryRecord record);

    int insertOrUpdateSelective(OpePartsDraftHistoryRecord record);

}
