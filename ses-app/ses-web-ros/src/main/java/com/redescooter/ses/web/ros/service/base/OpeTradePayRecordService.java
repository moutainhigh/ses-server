package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeTradePayRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeTradePayRecordService extends IService<OpeTradePayRecord> {

    int updateBatch(List<OpeTradePayRecord> list);

    int batchInsert(List<OpeTradePayRecord> list);

    int insertOrUpdate(OpeTradePayRecord record);

    int insertOrUpdateSelective(OpeTradePayRecord record);

}
