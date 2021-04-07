package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePayReceipt;

import java.util.List;

public interface OpePayReceiptService extends IService<OpePayReceipt> {

    int updateBatch(List<OpePayReceipt> list);

    int batchInsert(List<OpePayReceipt> list);

    int insertOrUpdate(OpePayReceipt record);

    int insertOrUpdateSelective(OpePayReceipt record);

}
