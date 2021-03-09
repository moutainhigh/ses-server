package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeInvoicePartsDetailB;

import java.util.List;

public interface OpeInvoicePartsDetailBService extends IService<OpeInvoicePartsDetailB> {


    int updateBatch(List<OpeInvoicePartsDetailB> list);

    int batchInsert(List<OpeInvoicePartsDetailB> list);

    int insertOrUpdate(OpeInvoicePartsDetailB record);

    int insertOrUpdateSelective(OpeInvoicePartsDetailB record);

}
