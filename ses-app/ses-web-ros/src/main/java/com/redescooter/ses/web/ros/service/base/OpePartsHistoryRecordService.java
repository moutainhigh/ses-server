package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsHistoryRecord;

@Transactional
public interface OpePartsHistoryRecordService extends IService<OpePartsHistoryRecord> {


    int updateBatch(List<OpePartsHistoryRecord> list);

    int batchInsert(List<OpePartsHistoryRecord> list);

    int insertOrUpdate(OpePartsHistoryRecord record);

    int insertOrUpdateSelective(OpePartsHistoryRecord record);

}



