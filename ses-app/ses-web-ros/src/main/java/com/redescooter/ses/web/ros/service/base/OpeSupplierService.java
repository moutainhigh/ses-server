package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSupplier;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSupplierService extends IService<OpeSupplier> {


    int updateBatch(List<OpeSupplier> list);

    int batchInsert(List<OpeSupplier> list);

    int insertOrUpdate(OpeSupplier record);

    int insertOrUpdateSelective(OpeSupplier record);

}

