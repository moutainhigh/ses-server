package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePayReceipt;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePayReceiptService extends IService<OpePayReceipt> {

    int updateBatch(List<OpePayReceipt> list);

    int batchInsert(List<OpePayReceipt> list);

    int insertOrUpdate(OpePayReceipt record);

    int insertOrUpdateSelective(OpePayReceipt record);

}
