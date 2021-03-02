package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpeInvoiceScooterB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeInvoiceScooterBService extends IService<OpeInvoiceScooterB> {


    int updateBatch(List<OpeInvoiceScooterB> list);

    int batchInsert(List<OpeInvoiceScooterB> list);

    int insertOrUpdate(OpeInvoiceScooterB record);

    int insertOrUpdateSelective(OpeInvoiceScooterB record);

}
