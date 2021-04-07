package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeInvoiceCombinB;

import java.util.List;

public interface OpeInvoiceCombinBService extends IService<OpeInvoiceCombinB> {


    int updateBatch(List<OpeInvoiceCombinB> list);

    int batchInsert(List<OpeInvoiceCombinB> list);

    int insertOrUpdate(OpeInvoiceCombinB record);

    int insertOrUpdateSelective(OpeInvoiceCombinB record);

}
