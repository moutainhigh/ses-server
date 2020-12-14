package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeInvoiceScooterDetailB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeInvoiceScooterDetailBService extends IService<OpeInvoiceScooterDetailB> {


    int updateBatch(List<OpeInvoiceScooterDetailB> list);

    int batchInsert(List<OpeInvoiceScooterDetailB> list);

    int insertOrUpdate(OpeInvoiceScooterDetailB record);

    int insertOrUpdateSelective(OpeInvoiceScooterDetailB record);

}
