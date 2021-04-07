package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrderPayment;

import java.util.List;

public interface OpeAssemblyOrderPaymentService extends IService<OpeAssemblyOrderPayment> {


    int updateBatch(List<OpeAssemblyOrderPayment> list);

    int batchInsert(List<OpeAssemblyOrderPayment> list);

    int insertOrUpdate(OpeAssemblyOrderPayment record);

    int insertOrUpdateSelective(OpeAssemblyOrderPayment record);

}

