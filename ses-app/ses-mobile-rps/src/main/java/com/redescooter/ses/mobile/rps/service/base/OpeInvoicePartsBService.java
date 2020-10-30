package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpeInvoicePartsB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeInvoicePartsBService extends IService<OpeInvoicePartsB> {


    int updateBatch(List<OpeInvoicePartsB> list);

    int batchInsert(List<OpeInvoicePartsB> list);

    int insertOrUpdate(OpeInvoicePartsB record);

    int insertOrUpdateSelective(OpeInvoicePartsB record);

}
