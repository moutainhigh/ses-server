package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeInvoiceCombinDetailB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeInvoiceCombinDetailBService extends IService<OpeInvoiceCombinDetailB> {


    int updateBatch(List<OpeInvoiceCombinDetailB> list);

    int batchInsert(List<OpeInvoiceCombinDetailB> list);

    int insertOrUpdate(OpeInvoiceCombinDetailB record);

    int insertOrUpdateSelective(OpeInvoiceCombinDetailB record);

}
