package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpeInvoicePartsDetailB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeInvoicePartsDetailBService extends IService<OpeInvoicePartsDetailB> {


    int updateBatch(List<OpeInvoicePartsDetailB> list);

    int batchInsert(List<OpeInvoicePartsDetailB> list);

    int insertOrUpdate(OpeInvoicePartsDetailB record);

    int insertOrUpdateSelective(OpeInvoicePartsDetailB record);

}
