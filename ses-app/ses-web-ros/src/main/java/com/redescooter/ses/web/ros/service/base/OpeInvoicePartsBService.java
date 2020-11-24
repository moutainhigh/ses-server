package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeInvoicePartsB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeInvoicePartsBService extends IService<OpeInvoicePartsB> {


    int updateBatch(List<OpeInvoicePartsB> list);

    int batchInsert(List<OpeInvoicePartsB> list);

    int insertOrUpdate(OpeInvoicePartsB record);

    int insertOrUpdateSelective(OpeInvoicePartsB record);

}
