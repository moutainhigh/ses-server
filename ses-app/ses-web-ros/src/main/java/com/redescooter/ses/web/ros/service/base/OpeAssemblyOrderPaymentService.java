package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrderPayment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeAssemblyOrderPaymentService extends IService<OpeAssemblyOrderPayment> {


    int updateBatch(List<OpeAssemblyOrderPayment> list);

    int batchInsert(List<OpeAssemblyOrderPayment> list);

    int insertOrUpdate(OpeAssemblyOrderPayment record);

    int insertOrUpdateSelective(OpeAssemblyOrderPayment record);

}

