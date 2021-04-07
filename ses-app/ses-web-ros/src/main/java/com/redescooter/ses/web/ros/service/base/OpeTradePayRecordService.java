package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeTradePayRecord;

import java.util.List;

public interface OpeTradePayRecordService extends IService<OpeTradePayRecord> {

    int updateBatch(List<OpeTradePayRecord> list);

    int batchInsert(List<OpeTradePayRecord> list);

    int insertOrUpdate(OpeTradePayRecord record);

    int insertOrUpdateSelective(OpeTradePayRecord record);

}
