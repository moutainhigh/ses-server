package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeInvoiceOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeInvoiceOrderService extends IService<OpeInvoiceOrder> {


    int updateBatch(List<OpeInvoiceOrder> list);

    int batchInsert(List<OpeInvoiceOrder> list);

    int insertOrUpdate(OpeInvoiceOrder record);

    int insertOrUpdateSelective(OpeInvoiceOrder record);

}
