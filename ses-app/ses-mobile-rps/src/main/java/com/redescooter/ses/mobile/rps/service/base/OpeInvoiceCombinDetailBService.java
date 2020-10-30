package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpeInvoiceCombinDetailB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeInvoiceCombinDetailBService extends IService<OpeInvoiceCombinDetailB> {


    int updateBatch(List<OpeInvoiceCombinDetailB> list);

    int batchInsert(List<OpeInvoiceCombinDetailB> list);

    int insertOrUpdate(OpeInvoiceCombinDetailB record);

    int insertOrUpdateSelective(OpeInvoiceCombinDetailB record);

}
