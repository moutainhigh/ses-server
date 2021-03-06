package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeInvoiceScooterB;

import java.util.List;

public interface OpeInvoiceScooterBService extends IService<OpeInvoiceScooterB> {


    int updateBatch(List<OpeInvoiceScooterB> list);

    int batchInsert(List<OpeInvoiceScooterB> list);

    int insertOrUpdate(OpeInvoiceScooterB record);

    int insertOrUpdateSelective(OpeInvoiceScooterB record);

}
