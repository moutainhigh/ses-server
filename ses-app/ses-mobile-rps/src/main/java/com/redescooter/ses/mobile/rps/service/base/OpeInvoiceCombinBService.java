package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpeInvoiceCombinB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeInvoiceCombinBService extends IService<OpeInvoiceCombinB> {


    int updateBatch(List<OpeInvoiceCombinB> list);

    int batchInsert(List<OpeInvoiceCombinB> list);

    int insertOrUpdate(OpeInvoiceCombinB record);

    int insertOrUpdateSelective(OpeInvoiceCombinB record);

}
