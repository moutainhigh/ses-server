package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSupplier;

import java.util.List;

public interface OpeSupplierService extends IService<OpeSupplier> {


    int updateBatch(List<OpeSupplier> list);

    int batchInsert(List<OpeSupplier> list);

    int insertOrUpdate(OpeSupplier record);

    int insertOrUpdateSelective(OpeSupplier record);

}

