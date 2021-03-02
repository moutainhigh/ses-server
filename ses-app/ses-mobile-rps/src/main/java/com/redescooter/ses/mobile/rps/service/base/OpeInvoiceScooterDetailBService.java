package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeInvoiceScooterDetailB;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeInvoiceScooterDetailBService extends IService<OpeInvoiceScooterDetailB> {


    int updateBatch(List<OpeInvoiceScooterDetailB> list);

    int batchInsert(List<OpeInvoiceScooterDetailB> list);

    int insertOrUpdate(OpeInvoiceScooterDetailB record);

    int insertOrUpdateSelective(OpeInvoiceScooterDetailB record);

}
